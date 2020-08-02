package com.das.swagger.impl;

import java.util.List;

import javax.swing.JButton;

import com.das.image.ImagePath;
import com.das.model.SwaggerResource;
import com.das.service.SwaggerService;
import com.das.swagger.SwaggerController;
import com.das.swagger.SwaggerModel;
import com.das.swagger.SwaggerView;
import com.drishlent.dlite.AbstractEmbeddedController;
import com.drishlent.dlite.ControllerActionHandler;
import com.drishlent.dlite.EmbeddedView;
import com.drishlent.dlite.annotation.Inject;
import com.drishlent.dlite.annotation.TabIndex;
import com.drishlent.dlite.annotation.UIComponent;
import com.drishlent.dlite.image.ImageButton;

@UIComponent
@TabIndex(value=3)
public class SwaggerControllerImpl extends AbstractEmbeddedController implements SwaggerController {

	private SwaggerView mView;
	private SwaggerModel mModel;
   
	@Inject
	private SwaggerService serverDetailsService;
	
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
		mModel = new SwaggerModelImpl();
		mModel.setController(this);
		mModel.initModel();

		mView = new SwaggerViewImpl();
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
		return "Swagger";
	}

	@Override
	public JButton getToolbarButton() {
		return ImageButton.createToolBarImageButton(ImagePath.IMAGE_SWAGGER, "Swagger");
	}

	@Override
	public void insert(SwaggerResource serverModel) throws Exception { 
		serverDetailsService.save(serverModel);
	}

	@Override
	public void insertAll(List<SwaggerResource> resources) throws Exception {
		serverDetailsService.saveAll(resources);
	}



}
