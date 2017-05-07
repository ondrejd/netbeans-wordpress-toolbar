/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ondrejd.wordpresstoolbar;

import org.netbeans.api.progress.ProgressHandle;
import org.openide.modules.ModuleInstall;
import org.openide.util.RequestProcessor;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        System.out.println("Installer.restored");
        Runnable importTask = new Runnable() {
            @Override
            public void run() {
                //...
                int steps = 1000;
                int stepDuration = 20;
                final ProgressHandle progr = ProgressHandle.createHandle("Known steps");
                int duration = steps * stepDuration;

                if (stepDuration == 0) {
                    progr.start(steps);
                } else {
                    progr.start(steps, duration);  // steps, seconds
                }

                for (int i = 1; i <= steps/* && !canceled*/; i++) {
                    //processFile(i, sleepTime);
                    progr.progress("File " + i + " processed", i);
                    System.out.println(" step " + i);
                }

                progr.finish();
            }
        };
        RequestProcessor.getDefault().post(importTask);
    }

}
