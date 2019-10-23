package ctrmap.humaninterface;

import ctrmap.CtrmapMainframe;
import java.awt.Dialog;
import javax.swing.JDialog;

/**
 * Modal dialog with a progress bar suitable for SwingWorkers.
 */
public class LoadingDialog extends javax.swing.JPanel {

	/**
	 * Creates new form LoadingDialog
	 */
	private JDialog dlg;

	public LoadingDialog() {
		initComponents();
	}

	public void setDescription(String text) {
		desc.setText(text);
	}

	public void setBarPercent(int val) {
		bar.setValue(val);
	}

	public void close() {
		dlg.dispose();
		dlg.setVisible(false);
	}

	public static LoadingDialog makeDialog(String desc) {
		LoadingDialog ret = new LoadingDialog();
		ret.dlg = new JDialog();
		ret.dlg.add(ret);
		ret.setDescription(desc);
		ret.setBarPercent(0);
		ret.dlg.setUndecorated(true);
		ret.dlg.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		ret.dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		ret.dlg.pack();
		return ret;
	}

	public void showDialog() {
		if (CtrmapMainframe.frame != null) {
			dlg.setLocationRelativeTo(CtrmapMainframe.frame);
		}
		dlg.setVisible(true);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bar = new javax.swing.JProgressBar();
        desc = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setMinimumSize(new java.awt.Dimension(186, 75));

        desc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        desc.setText("Sample text");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(desc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(desc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar bar;
    private javax.swing.JLabel desc;
    // End of variables declaration//GEN-END:variables
}
