package com.robot.cservice.comm;

import com.squareup.otto.Bus;

/**
 * @author: joey
 * @function: BusProvider
 * @date: 2018/12/27
 */

public class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
    }
}
