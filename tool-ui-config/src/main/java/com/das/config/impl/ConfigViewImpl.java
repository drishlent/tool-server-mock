package com.das.config.impl;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.das.config.ConfigModel;
import com.das.config.ConfigView;
import com.das.model.ServerModel;
import com.drishlent.dlite.AbstractEmbeddedView;
import com.drishlent.dlite.EmbeddedModel;
import com.drishlent.dlite.RDSwingWorker;
import com.drishlent.dlite.table.RDTableModel;
import com.drishlent.dlite.table.RDTablePanel;
import com.drishlent.dlite.validator.NumberAcceptValidator;



public class ConfigViewImpl extends AbstractEmbeddedView implements ConfigView {
	
	private JPanel mMainPanel;
	private ConfigModel mModel;
	private JTable serverTable;
	
	@Override
	public JPanel getPanel() {
		JPanel lIntialPanel = new JPanel(new GridLayout(3,1));
		
		lIntialPanel.add(getServerCreationPanel());
		lIntialPanel.add(new JPanel());
		lIntialPanel.add(getServerStartStopPanel());
		
		mMainPanel.add(lIntialPanel, BorderLayout.NORTH);	
		mMainPanel.add(createTableView(), BorderLayout.CENTER);	
		
	    return mMainPanel;
	}

	@Override
	public String getTitle() {
		return "Home";
	}

	@Override
	public void setModel(EmbeddedModel pModel) {
		mModel=	(ConfigModel) pModel;
	}

	@Override
	public void startView() {
		
	}

	@Override
	public void initView() {
	    super.initView();
	    mMainPanel = new JPanel(new BorderLayout());
	    
	}
	
	private JPanel getServerCreationPanel() {
		JPanel serverCreationPanel = new JPanel();
		serverCreationPanel.add(new JLabel(createBoldText("Server Name: ")));
		final JTextField serverName = new JTextField(50);
		serverCreationPanel.add(serverName);
		
		serverCreationPanel.add(new JLabel(createBoldText("Port: ")));
		final JTextField serverPort = new JTextField(10);
		serverPort.setDocument(new NumberAcceptValidator());
		serverCreationPanel.add(serverPort);
		
		final JCheckBox checkBox = new JCheckBox(createBoldText("enable auto server run mode"));
		
		serverCreationPanel.add(checkBox);
		final JButton submit = new JButton(createBoldText("Save"));
		serverCreationPanel.add(submit);
		
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 mModel.createServerConf(serverName.getText(), serverPort.getText(), checkBox.isSelected());	
			}
		});
		
	    return serverCreationPanel;
	}
	
	private JPanel getServerStartStopPanel() {
		JPanel serverStartStopPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));	
		
		final JButton start = new JButton(createBoldText("Start"));
		final JButton stop = new JButton(createBoldText("Stop"));
		
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(serverTable.getSelectedRowCount() >= 1){
					@SuppressWarnings("unchecked")
					List<ServerModel> allServerModel = (List<ServerModel>) ((RDTableModel) serverTable.getModel()).getData();
					final ServerModel serverModel = allServerModel.get(serverTable.convertRowIndexToModel(serverTable.getSelectedRow()));
					
					RDSwingWorker<ServerModel> lRDSwingWorker = new RDSwingWorker<ServerModel>() {
						
						@Override
						protected ServerModel doInBackground() throws Exception {
							getController().getStatusBarEvaluator().init();
							getController().getStatusBarEvaluator().get1StLabelMessage().setText("getting started");
							((ConfigControllerImpl)getController()).startServer(serverModel);
							return serverModel;
						}
						
						@Override
						protected void finished() throws Exception {
							ServerModel dbServerModel = get(); 
							getController().getStatusBarEvaluator().done();
							getController().getStatusBarEvaluator().get1StLabelMessage().setText("server started");
							((RDTableModel) serverTable.getModel()).updateTableLine(dbServerModel);	
						}
					};
						
					lRDSwingWorker.execute();	
				}
			}
		});
		
		stop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(serverTable.getSelectedRowCount() >= 1){
					@SuppressWarnings("unchecked")
					List<ServerModel> allServerModel = (List<ServerModel>) ((RDTableModel) serverTable.getModel()).getData();
					final ServerModel serverModel = allServerModel.get(serverTable.convertRowIndexToModel(serverTable.getSelectedRow()));
					RDSwingWorker<ServerModel> lRDSwingWorker = new RDSwingWorker<ServerModel>() {
						
						@Override
						protected ServerModel doInBackground() throws Exception {
							getController().getStatusBarEvaluator().init();
							getController().getStatusBarEvaluator().get1StLabelMessage().setText("getting stoped");
							((ConfigControllerImpl)getController()).stopServer(serverModel);
							return serverModel;
						}
						
						@Override
						protected void finished() throws Exception {
							ServerModel dbServerModel = get(); 
							getController().getStatusBarEvaluator().done();
							getController().getStatusBarEvaluator().get1StLabelMessage().setText("server stoped");
							((RDTableModel) serverTable.getModel()).updateTableLine(dbServerModel);	
						}
					};
						
					lRDSwingWorker.execute();
				}
			}
		});
		
		serverStartStopPanel.add(start);
		serverStartStopPanel.add(stop);
		
		
		return serverStartStopPanel;
	}
	
	private JPanel createTableView() {
		JPanel lInsert = new JPanel(new BorderLayout());
		RDTablePanel lTablePanel = new RDTablePanel(mModel.getServerTableModel(),	null, true);
		serverTable = lTablePanel.getTable();
		JScrollPane lScrollPane = new JScrollPane(serverTable);
		lScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		lInsert.add(lScrollPane, BorderLayout.CENTER);
		
		return lInsert;
	}
	
}
