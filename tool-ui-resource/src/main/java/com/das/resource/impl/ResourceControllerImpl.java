package com.das.resource.impl;

import java.util.List;

import javax.swing.JButton;

import com.das.image.ImagePath;
import com.das.model.Resource;
import com.das.model.ServerInfo;
import com.das.resource.ResourceController;
import com.das.resource.ResourceModel;
import com.das.resource.ResourceView;
import com.das.service.ResourceService;
import com.drishlent.dlite.AbstractEmbeddedController;
import com.drishlent.dlite.ControllerActionHandler;
import com.drishlent.dlite.EmbeddedView;
import com.drishlent.dlite.annotation.Inject;
import com.drishlent.dlite.annotation.TabIndex;
import com.drishlent.dlite.annotation.UIComponent;
import com.drishlent.dlite.image.ImageButton;

@UIComponent
@TabIndex(value=2)
public class ResourceControllerImpl extends AbstractEmbeddedController implements ResourceController {

	private ResourceView mView;
	private ResourceModel mModel;
   
	@Inject
	private ResourceService serverDetailsService;
	
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
		mModel = new ResourceModelImpl();
		mModel.setController(this);
		mModel.initModel();

		mView = new ResourceViewImpl();
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
		return ImageButton.createToolBarImageButton(ImagePath.IMAGE_RESOURCE, "Resources");
	}

	@Override
	public void insert(Resource serverModel) throws Exception { 
		serverDetailsService.save(serverModel);
	}

	@Override
	public List<Resource> loadAll() throws Exception {
		return serverDetailsService.findAll();
	}

	@Override
	public List<ServerInfo> findAllServer() throws Exception {
		return serverDetailsService.findAllServer();
	}

	@Override
	public void publish(List<Resource> resources, String serverName) throws Exception {
		serverDetailsService.publish(resources, serverName);
	}

	@Override
	public void unpublish(List<Resource> resources) throws Exception {
		serverDetailsService.unpublish(resources);
	}


}
