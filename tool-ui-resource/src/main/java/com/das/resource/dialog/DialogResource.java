package com.das.resource.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.das.model.Resource;
import com.das.resource.ResourceModel;
import com.das.resource.panel.ResourceInputPanel;

public class DialogResource extends JDialog {

	private static final long serialVersionUID = 1L;
	private final ResourceModel mModel;
	private final Resource resource;
	JButton mOkButton;

	public DialogResource(JFrame pParentFrame, String pTitel, ResourceModel pModel, Resource resource) {
		super(pParentFrame, pTitel, true);
		//setSize(360, 210);
		setLocationRelativeTo(pParentFrame);
		mModel = pModel;
		this.resource = resource;
		Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
		setIconImage(icon);
		init();
		setVisible(true);
	}
	
	private void init() {
		JPanel lSouthPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton lCancelButton = new JButton("Cancel");
		lCancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				setVisible(false);
				dispose();
			}
		});
		mOkButton = new JButton("OK");
		mOkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
			}
		});
		lSouthPanel.setMinimumSize(new Dimension(340, 30));
		lSouthPanel.setPreferredSize(new Dimension(340, 30));
		lSouthPanel.add(mOkButton); 
		lSouthPanel.add(lCancelButton); 
		JPanel lFahrzeugPanel = new JPanel(new FlowLayout());
		lFahrzeugPanel.add(lSouthPanel);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new ResourceInputPanel(mModel, resource).getResourceInputView(), BorderLayout.CENTER);
		getContentPane().add(lSouthPanel, BorderLayout.SOUTH);

	}

	
}