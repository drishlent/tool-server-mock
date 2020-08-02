package com.das.config.impl;

import java.util.List;

import javax.swing.JButton;

import com.das.config.ConfigController;
import com.das.config.ConfigModel;
import com.das.config.ConfigView;
import com.das.image.ImagePath;
import com.das.model.ServerModel;
import com.das.service.ServerDetailsService;
import com.drishlent.dlite.AbstractEmbeddedController;
import com.drishlent.dlite.ControllerActionHandler;
import com.drishlent.dlite.EmbeddedView;
import com.drishlent.dlite.annotation.Inject;
import com.drishlent.dlite.annotation.TabIndex;
import com.drishlent.dlite.annotation.UIComponent;
import com.drishlent.dlite.image.ImageButton;

@UIComponent
@TabIndex(value=1)
public class ConfigControllerImpl extends AbstractEmbeddedController implements ConfigController {

	private ConfigView mView;
	private ConfigModel mModel;
   
	@Inject
	private ServerDetailsService serverDetailsService;
	
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
		mModel = new ConfigModelImpl();
		mModel.setController(this);
		mModel.initModel();

		mView = new ConfigViewImpl();
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
		return "Config";
	}

	@Override
	public JButton getToolbarButton() {
		return ImageButton.createToolBarImageButton(ImagePath.IMAGE_CONFIG, "Config");
	}

	@Override
	public void insert(ServerModel serverModel) throws Exception { 
		serverDetailsService.save(serverModel);
	}

	@Override
	public List<ServerModel> loadAll() throws Exception {
		return serverDetailsService.findAll();
	}

	@Override
	public void startServer(ServerModel serverModel) {
		serverDetailsService.startServer(serverModel);
	}

	@Override
	public void stopServer(ServerModel serverModel) {
		serverDetailsService.stopServer(serverModel);
	}

}
