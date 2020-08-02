package com.das.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import org.apache.log4j.Logger;

public class DialogMessage extends JDialog {

	private static final long serialVersionUID = 1L;
	private final static Logger mLogger = Logger.getLogger(DialogMessage.class);
	private JButton mOkButton;
	private JTextPane  textArea;
    private boolean mIsEditable;
    private JFrame mParentFrame;
    private String mMsg;
    
	public DialogMessage(JFrame pParentFrame, String pTitel, String pMsg, boolean isEditable) {
		super(pParentFrame, pTitel, true);
		mParentFrame = pParentFrame;
		mMsg = pMsg;
		//setSize(760, 610);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int)((screenSize.width)*0.4), (int)((screenSize.height)*0.7));
		textArea = new JTextPane();
		textArea.setText("");
		textArea.setEditable(isEditable);
		textArea.setBackground(Color.lightGray);
		mIsEditable = isEditable;
		setLocationRelativeTo(pParentFrame);
		//Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
		//setIconImage(icon);
		init();
		setVisible(true);
	}

	public String getModifiedLines() {
		return mMsg;
	}

	private void init() {
		JPanel lSouthPanel = new JPanel(new GridLayout(1, 2));

		JPanel lSouthPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel lSouthPanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JButton lCancelButton = new JButton("Cancel");
		lCancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				mMsg = null;
				setVisible(false);
				dispose();
			}
		});
		mOkButton = new JButton("OK");
		mOkButton.setEnabled(mIsEditable);
		mOkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String content = textArea.getText();
				mMsg = content;
				setVisible(false);
				dispose();
			}
		});

		
		lSouthPanel2.add(mOkButton);
		lSouthPanel2.add(lCancelButton);
		
		lSouthPanel.add(lSouthPanel1);
		lSouthPanel.add(lSouthPanel2);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getItemdetailsTable(), BorderLayout.CENTER);
		getContentPane().add(lSouthPanel, BorderLayout.SOUTH);

	}

	private JPanel getItemdetailsTable() {
		textArea.setText(mMsg);
		JScrollPane scrollPane = new JScrollPane(textArea);

		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JPanel lPanel = new JPanel(new BorderLayout());
		lPanel.add(scrollPane, BorderLayout.CENTER);

		return lPanel;
	}

	
}
