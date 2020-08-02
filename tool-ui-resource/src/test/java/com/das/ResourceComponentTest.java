package com.das;

import com.das.resource.impl.ResourceControllerImpl;
import com.drishlent.dlite.annotation.TestUIComponent;
import com.drishlent.dlite.junit.BaseUI;


@TestUIComponent(componentClass=ResourceControllerImpl.class, waitFor=5)
public class ResourceComponentTest extends BaseUI {
	
}
