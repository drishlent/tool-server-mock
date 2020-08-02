package com.das.home.impl;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URL;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.das.home.HomeView;
import com.drishlent.dlite.AbstractEmbeddedView;
import com.drishlent.dlite.EmbeddedModel;



public class HomeViewImpl extends AbstractEmbeddedView implements HomeView {
	
	private JPanel mMainPanel;
	
	@Override
	public JPanel getPanel() {
		mMainPanel.add(getServerCreationPanel(), BorderLayout.CENTER);	
		
	    return mMainPanel;
	}

	@Override
	public String getTitle() {
		return "Home";
	}

	@Override
	public void setModel(EmbeddedModel pModel) {
		
	}

	@Override
	public void startView() {
		
	}

	@Override
	public void initView() {
	    super.initView();
	    mMainPanel = new JPanel(new BorderLayout());
	}
	
	private JScrollPane getServerCreationPanel() {
		JTextPane pane = new JTextPane();
		URL url = HomeViewImpl.class.getResource("home.html");
		try {
			pane.setPage(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		pane.setEditable(false);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().add(pane);
		
	    return scrollPane;
	}
	
	
}
