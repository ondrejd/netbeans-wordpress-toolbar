<?php
/**
 * Script that parses hooks from WordPress Code Reference 
 * and updates `wordpress-api.xml` with loaded data.
 *
 * Usage:
 *
 *   php parse-wordpress-api.php
 *
 * @author Ondřej Doněk, <ondrejd@gmail.com>
 * @package netbeans-wordpress-plugin
 */

namespace com\ondrejd\WordPressToolbar;

/**
 * Parser for Code References.
 */
class ReferenceParser {
    const AVAIL_TYPES  = ['classes', 'functions', 'hooks'];
    const DEFAULT_FILE = 'wordpress-api.xml';
    const SEARCH_URL  = 'https://developer.wordpress.org/reference/%s/page/%d/';

    /**
     * Output file.
     * @var string
     */
    private $file;

    /**
     * Array with reference items.
     * @var array
     */
    private $refs;

    /**
     * Holds parser errors.
     * @var array
     */
    private $errors;
    
    /**
     * Constructor.
     * @param string $file Output XML file.
     */
    public function __construct( $file = self::DEFAULT_FILE ) {
        $this->file    = empty( $file ) ? self::DEFAULT_FILE : $file;
        $this->refs    = array( 'classes' => array(), 'functions' => array(), 'hooks' => array() );
        $this->errors  = array();
    }

    /**
     * Returns the last parser error.
     * @return string
     */
    public function lastError() {
        return $this->errors[count( $this->errors )];
    }

    /**
     * Parse WordPress Code Reference.
     * @return bool
     */
    public function parse() {
        // If file already exists error is occured
        if ( @file_exists( $this->file ) ) {
            $this->errors[] = sprintf( "Output file \"%s\" already exists!", $this->file );
            return false;
        }

        // Parse whole reference site
        foreach ( self::AVAIL_TYPES as $type ) {
            $page = 1;
            do {
                $search_url = sprintf( self::SEARCH_URL, $type, $page );
                $content = @file_get_contents( $search_url );

                if ( strlen( $content ) > 0 || ! empty( $content ) ) {
                    $this->parsePage( $type, $content );
                }

                $page++;
            } while ( ! empty( $content ) || $content !== false );
        }

        // No items were parsed
        if ( count( $this->refs[$type] ) == 0 ) {
            $this->errors[] = "No items were parsed!";
            return false;
        }

        // Write XML file
        if ( $this->saveXml() !== true ) {
            $this->errors[] = "Writing output XML file failed!";
            return false;
        }

        return true;
    }

    /**
     * @internal Parses single code reference page.
     * @param string $type
     * @param string $content
     */
    private function parsePage( $type, $content ) {
        // For this style class we will search
        $cls = '';
        switch ( $type ) {
            case 'classes'   : $cls = 'class'; break;
            case 'functions' : $cls = 'function'; break;
            case 'hooks'     : $cls = 'hook'; break;
        }

        // Parse content
        try {
            $html = @\DOMDocument::loadHTML( $content );
            $articles = $html->getElementsByTagName( 'article' );

            for ( $i = 0; $i < $articles->length; $i++ ) {
                $article = $articles->item( $i );

                if ( strpos( $article->getAttribute( 'class' ), $cls ) !== false ) {
                    $this->parseArticle( $type, $article );
                }
            }
        } catch ( \Exception $e ) {
            // ... nothing to do ...
        }
    }

    /**
     * @internal Parses single article (resource - class, function or hook).
     * @param string $type
     * @param \DOMElement $article
     */
    private function parseArticle( $type, \DOMElement $article ) {
        try {
            $name = trim( $article->getElementsByTagName( 'h1' )->item( 0 )->textContent );
            $desc = '';
            $divs = $article->getElementsByTagName( 'div' );

            for ( $i = 0; $i < $divs->length; $i++ ) {
                $div = $divs->item( $i );

                if ( $div->getAttribute( 'class' ) == 'description' ) {
                    $desc = trim( $div->textContent );
                }
            }

            $this->refs[$type][] = new ReferenceItem( $name, $desc );
        } catch ( \Exception $e ) {
            // ... nothing to do ...
        }
    }

    /**
     * Writes output XML file.
     * @return bool
     */
    private function saveXml() {
        // Start XML
        $xml  = '<?xml version="1.0" charset="utf-8"?>' . PHP_EOL;
        $xml .= '<api datetime="' . date( 'Y-m-d H:i:s' ) . '">' . PHP_EOL;

        // Write all resources
        foreach ( self::AVAIL_TYPES as $type ) {
            $xml .= "<{$type}>" . PHP_EOL;
            foreach ( $this->refs[$type] as $itm ) {
                $xml .= $itm->__toString();
            }
            $xml .= "</{$type}>" . PHP_EOL;
        }

        // Finish XML
        $xml .= '</api>';

        // Write file
        $res = file_put_contents( $this->file, $xml );

        return ( $res == 0 || $res === false ) ? false : true;
    }
}

/**
 * Single reference item.
 */
class ReferenceItem {
    /**
     * @var string Name of reference item.
     */
    private $name;

    /**
     * @var string Description of reference item.
     */
    private $desc;

    /**
     * Constructor.
     * @param string $type
     * @param string $name
     * @param string $desc
     */
    public function __construct( $name = null, $desc = null ) {
        $this->name = $name;
        $this->desc = $desc;
    }

    /**
     * @param string $property
     * @return string
     * @throws \Exception Whenever given property is not found.
     */
    public function __get( $property ) {
        if ( ! property_exists( $this, $property ) && $propety != 'description' ) {
            throw new \Exception( "Given property \"{$property}\" was not found!" );
        }
        return ( $property == 'description' ) ? $this->desc : $this->{$property};
    }

    /**
     * @param string $prop
     * @return string $value
     * @throws \Exception Whenever given property is not found.
     */
    public function __set( $property, $value ) {
        if ( ! property_exists( $this, $property ) ) {
            throw new \Exception( "Given property \"{$property}\" was not found!" );
        }
        $this->{$property} = $value;
    }

    /**
     * @return string
     */
    public function __toString() {
        if ( empty( $this->name ) ) {
            return '';
        }

        $out  = "<item name=\"{$this->name}\"";
        $out .= empty( $this->desc ) ? '' : " description=\"{$this->desc}\"";
        $out .= "/>";

        return $out;
    }
}

// ==========================================================================

/**
 * Our parser.
 * @var Parser
 */
$parser = new ReferenceParser();

// Parse WordPress Code Reference
if ( $parser->parse() === false ) {
    printf( "Parsing failed!\n%s\n", $parser->lastError() );
}
