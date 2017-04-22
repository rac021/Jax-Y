
package com.rac021.jaxy.override.configuration ;

import com.rac021.jax.api.streamers.IStreamerConfigurator ;
import com.rac021.jax.api.streamers.DefaultStreamerConfigurator ;

/**
 *
 * @author ryahiaoui
 */

public class Configuration extends DefaultStreamerConfigurator implements IStreamerConfigurator {

    public Configuration()              {

        super()                         ;

        super.setRatio(1)               ;
        super.setNbrCores(2 )           ;
        super.setRecorderLenght( 5000 ) ;
        
    }
 
}

