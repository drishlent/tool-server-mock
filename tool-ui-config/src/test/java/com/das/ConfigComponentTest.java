package com.das;

import com.das.config.impl.ConfigControllerImpl;
import com.drishlent.dlite.annotation.TestUIComponent;
import com.drishlent.dlite.junit.BaseUI;


@TestUIComponent(componentClass=ConfigControllerImpl.class, waitFor=5)
public class ConfigComponentTest extends BaseUI {
	
}
