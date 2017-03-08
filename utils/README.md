# NetBeans WordPress Toolbar - Utilities

Here are located some utility scripts:

## Parse WordPress API

Script `parse-wordpress-api.php` is used for creating `wordpress-api.xml` file. It parses site [https://developer.wordpress.org/reference/](1) for _actions_, _functions_ and _hooks_.

Usage:

```
php parse-wordpress-api.php
```

This will parse all data from [Code Reference](1) and save data into `wordpress-api.xml` file which can be used inside our plugin.

[1]: https://developer.wordpress.org/reference/
