package com.das.home.impl;

import javax.swing.JButton;

import com.das.home.HomeController;
import com.das.home.HomeModel;
import com.das.home.HomeView;
import com.das.image.ImagePath;
import com.drishlent.dlite.AbstractEmbeddedController;
import com.drishlent.dlite.ControllerActionHandler;
import com.drishlent.dlite.EmbeddedView;
import com.drishlent.dlite.annotation.TabIndex;
import com.drishlent.dlite.annotation.UIComponent;
import com.drishlent.dlite.image.ImageButton;

@UIComponent(isLoginSuccesPage=true)
@TabIndex
public class HomeControllerImpl extends AbstractEmbeddedController implements HomeController {

	private HomeView mView;
	private HomeModel mModel;
   
	@Override
	public void accept(ControllerActionHandler pActionHandler) {
		pActionHandler.vist(this);
	}

	@Override
	public EmbeddedView getView() {
		return mView;
	}

	@Override
	public void initController() {
		mModel = new HomeModelImpl();
		mModel.setController(this);
		mModel.initModel();

		mView = new HomeViewImpl();
		mView.setModel(mModel);
		mView.setController(this);
		mView.initView();
	}

	@Override
	public void startController() {
		mModel.startModel();
		mView.startView();
	}


	@Override
	public String get1stLebelBarMsg() {
		return "Home";
	}

	@Override
	public JButton getToolbarButton() {
		return ImageButton.createToolBarImageButton(ImagePath.IMAGE_HOME, "Home");
	}

}
