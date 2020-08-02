package com.das;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class SplashLauncher extends JWindow {

	private static final long serialVersionUID = 1L;
	public static String ERROR_MASSAGE = "Unable to run";
	private JLabel mDynaminLabel;
	private static SplashLauncher mSplashManual;
	private SplashStatus mSplashStatus;
    
	public SplashLauncher() {
	}

	public SplashStatus displaySplash() {
		JPanel lSplashPanel = (JPanel) getContentPane();
		lSplashPanel.setBackground(Color.BLUE);

		ImageIcon lSplashImage = getImageIcon("dummy.png");
		int lWidth = lSplashImage.getIconWidth();
		int lHeight = lSplashImage.getIconHeight();
		Dimension lScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (lScreenSize.width - lWidth) / 2;
		int y = (lScreenSize.height - lHeight) / 2;

		setBounds(x, y, lWidth, lHeight);
		JLabel lImagelabel = new JLabel(lSplashImage);

		mDynaminLabel = new JLabel();
		mDynaminLabel.setForeground(Color.YELLOW);
		mDynaminLabel.setText("<html><center>Load...<center></html>");
		mDynaminLabel.setLocation(10, 10);
		mDynaminLabel.setPreferredSize(new Dimension(250, 20));
		mDynaminLabel.setMaximumSize(new Dimension(250, 20));
		mDynaminLabel.setMinimumSize(new Dimension(250, 20));
		mDynaminLabel.setSize(mDynaminLabel.getPreferredSize());

		//lImagelabel.add(mDynaminLabel);
		lImagelabel.setHorizontalTextPosition(JLabel.CENTER);
		lImagelabel.setVerticalTextPosition(JLabel.CENTER);
		lSplashPanel.add(lImagelabel, BorderLayout.CENTER);
		mSplashStatus = new SplashStatus();
		setVisible(true);
		setAlwaysOnTop(true);
		setLocation(1155, 40);
		try {
			Thread.sleep(2000);

		} catch (Exception e) {
		}
		new LockTimer();
		return mSplashStatus;
	}

	public static void main(String[] pArgs) {
		mSplashManual = new SplashLauncher();
		mSplashManual.displaySplash();
	}

	public void dispose() {
		super.dispose();
	}

	private class LockTimer implements ActionListener {
		JLabel mTimeLabel;
		int mIndex = 0;
		javax.swing.Timer mTimer;
		// int minimum = mProgressBar.getMinimum();
		int mBarMulti = 0;

		//boolean isInUse = false;
		
		private LockTimer() {
			mTimeLabel = mDynaminLabel;
			mTimeLabel.setText(new Date().toString());
			try {
				mTimeLabel.setText("Checking         ...");

			} catch (Exception x) {
				System.out.println("Exception : " + x);
				throw x;
			}
			mTimer = new javax.swing.Timer(1000, this);
			mTimer.start();
		}

		public void actionPerformed(ActionEvent e) {
			
					mTimeLabel.setText("lMsg");
					mTimeLabel.revalidate();
					
		}

	}

	public static class SplashStatus {

		private boolean mDispose = false;
		private boolean mException = false;

		public void setDispose(boolean pDispose) {
			mDispose = pDispose;
		}

		public boolean isDispose() {
			return mDispose;
		}
		
		public void setException(boolean pException) {
			mException = pException;
		}
		
		public boolean isException() {
			return mException;
		}
	}
	
    public static ImageIcon getImageIcon(String pFile ){
		
		ImageIcon lIcon;
		try{
			URL url = Class.forName(SplashLauncher.class.getName()).getResource("images/"+pFile);
			
			if(url == null)
			     url = ClassLoader.getSystemResource("com/pask/image/images/"+pFile);
			
			//System.out.println("url : "+url);	
			lIcon = new ImageIcon(url);
		
		}
		catch(Exception e){
			lIcon = new ImageIcon();
		}
		
		return lIcon;
		
	}

}
