package com.das;

import com.das.swagger.impl.SwaggerControllerImpl;
import com.drishlent.dlite.annotation.TestUIComponent;
import com.drishlent.dlite.junit.BaseUI;


@TestUIComponent(componentClass=SwaggerControllerImpl.class, waitFor=15)
public class SwaggerComponentTest extends BaseUI {
	
}
