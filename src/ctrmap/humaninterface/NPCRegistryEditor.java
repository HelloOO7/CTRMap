package ctrmap.humaninterface;

import ctrmap.CtrmapMainframe;
import ctrmap.Utils;
import ctrmap.Workspace;
import ctrmap.formats.containers.MM;
import ctrmap.formats.h3d.BCHFile;
import ctrmap.formats.npcreg.NPCRegistry;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.text.NumberFormatter;

/**
 * Regedit class for NPC registries, more comprehensible than PRE.
 */
public class NPCRegistryEditor extends javax.swing.JFrame {

	/**
	 * Creates new form NPCRegistryEditor
	 */
	private NPCRegistry reg;
	private NPCRegistry.NPCRegistryEntry e;
	private DefaultListModel<String> model = new DefaultListModel<>();
	private ArrayList<Integer> dict = new ArrayList<>();

	public NPCRegistryEditor() {
		initComponents();
		setUnsignedByteValueClass(new JFormattedTextField[]{shadowType, areaW, areaH});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (saveEntry(true)) {
					dispose();
				}
			}
		});
		setVisible(true);
	}

	public void setUnsignedByteValueClass(JFormattedTextField[] fields) {
		for (int i = 0; i < fields.length; i++) {
			NumberFormatter formatter = (NumberFormatter) fields[i].getFormatter();
			formatter.setValueClass(Integer.class);
			formatter.setMaximum(255);
		}
	}

	public void loadRegistry(NPCRegistry reg) {
		this.reg = reg;
		entryList.removeAll();
		model.removeAllElements();
		entryList.setModel(model);
		if (reg != null) {
			for (Map.Entry<Integer, NPCRegistry.NPCRegistryEntry> entry : reg.entries.entrySet()) {
				model.addElement("UID: " + entry.getValue().uid + " | Model: " + entry.getValue().model);
				dict.add(entry.getValue().uid);
			}
		}
		entryCountLabel.setText("Entry count: " + model.getSize());
		entryList.addListSelectionListener((ListSelectionEvent e) -> {
			if (entryList.getSelectedValue() == null || entryList.getSelectedIndex() == -1) {
				return;
			}
			saveEntry(false);
			showEntry(dict.get(entryList.getSelectedIndex()));
		});
	}

	public void setEntry(int id) {
		entryList.setSelectedIndex(dict.indexOf(id));
	}

	public void showEntry(int id) {
		if (reg == null || !reg.entries.containsKey(id)) {
			return;
		}
		NPCRegistry.NPCRegistryEntry entry = reg.entries.get(id);
		uid.setValue(entry.uid);
		mdl.setValue(entry.model);
		areaW.setValue(entry.collW);
		areaH.setValue(entry.collH);
		render.setSelected(entry.renderEnabled != 0);
		shadow.setSelected(entry.shadowEnabled != 0);
		shadowType.setValue(entry.shadowEnabled);
		item.setSelected(entry.isItOb == 0);
		dummy.setSelected(entry.isDummy == 1);
		e = entry;
		File bchFile = Workspace.getWorkspaceFile(Workspace.ArchiveType.MOVE_MODELS, entry.model);
		if (bchFile.exists()) {
			BCHFile mdlBch = new BCHFile(new MM(bchFile).getFile(0));
			mdlBch.models.get(0).setMaterialTextures(mdlBch.textures);
			customH3DPreview1.loadModel(mdlBch.models.get(0));
		} else {
			customH3DPreview1.loadModel(null);
		}
	}

	public boolean saveEntry(boolean dialog) {
		if (reg.entries.containsKey((Integer)uid.getValue()) && !reg.entries.get((Integer)uid.getValue()).equals(e)) {
			JOptionPane.showMessageDialog(this, "The specified UID is already registered. Please use another one.", "UID not unique", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (e != null) {
			NPCRegistry.NPCRegistryEntry e2 = new NPCRegistry.NPCRegistryEntry();
			e2.uid = (Integer) uid.getValue();
			e2.model = (Integer) mdl.getValue();
			if (render.isSelected()) {
				if (e.renderEnabled != 0) {
					e2.renderEnabled = e.renderEnabled;
				} else {
					e2.renderEnabled = 2;
				}
			} else {
				e2.renderEnabled = 0;
			}
			e2.shadowEnabled = (Integer) shadowType.getValue();
			e2.isItOb = item.isSelected() ? 0 : 1;
			e2.isDummy = dummy.isSelected() ? 1 : 0;
			e2.collW = (Integer) areaW.getValue();
			e2.collH = (Integer) areaH.getValue();
			e2.u1 = e.u1;
			e2.u4 = e.u4;
			e2.u8 = e.u8;
			e2.u9 = e.u9;
			e2.uC = e.uC;
			e2.u11 = e.u11;
			if (!e2.equals(e)) {
				if (dialog) {
					switch (Utils.showSaveConfirmationDialog("NPC registry")) {
						case JOptionPane.YES_OPTION:
							break;
						case JOptionPane.NO_OPTION:
							return true;
						case JOptionPane.CANCEL_OPTION:
							return false;
					}
				}
				model.setElementAt("UID: " + e2.uid + " | Model: " + e2.model, dict.indexOf(e.uid));
				dict.set(dict.indexOf(e.uid), e2.uid);
				reg.entries.remove(e.uid);
				reg.models.remove(e.uid);
				e = e2;
				reg.entries.put(e.uid, e);
				reg.mapModel(e.uid, e.model);
				reg.modified = true;
				showEntry(e.uid);
			}
		}
		return true;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        uidLabel = new javax.swing.JLabel();
        uid = new javax.swing.JSpinner();
        mdlLabel = new javax.swing.JLabel();
        mdl = new javax.swing.JSpinner();
        headerSep = new javax.swing.JSeparator();
        render = new javax.swing.JCheckBox();
        shadow = new javax.swing.JCheckBox();
        item = new javax.swing.JCheckBox();
        dummy = new javax.swing.JCheckBox();
        collLabel = new javax.swing.JLabel();
        shadowType = new javax.swing.JFormattedTextField();
        areaWLabel = new javax.swing.JLabel();
        areaHLabel = new javax.swing.JLabel();
        areaH = new javax.swing.JFormattedTextField();
        btnSave = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        entryList = new javax.swing.JList<>();
        listSep = new javax.swing.JSeparator();
        entryCountLabel = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        customH3DPreview1 = new ctrmap.humaninterface.CustomH3DPreview();
        areaW = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("NRE");
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(255, 271));
        setResizable(false);

        uidLabel.setText("UID");

        uid.setModel(new javax.swing.SpinnerNumberModel(0, null, 65535, 1));

        mdlLabel.setText("Source file");

        mdl.setModel(new javax.swing.SpinnerNumberModel(0, null, 65535, 1));

        render.setText("Render this model");

        shadow.setText("Draw shadow sprite | Type:");
        shadow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shadowActionPerformed(evt);
            }
        });

        item.setText("This is an item/object");

        dummy.setText("This is a dummy model");

        collLabel.setText("Collision area:");

        shadowType.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        areaWLabel.setText("Width");

        areaHLabel.setText("Height");

        areaH.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        areaH.setPreferredSize(new java.awt.Dimension(75, 20));

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(entryList);

        listSep.setOrientation(javax.swing.SwingConstants.VERTICAL);

        entryCountLabel.setText("Entry count:");

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout customH3DPreview1Layout = new javax.swing.GroupLayout(customH3DPreview1);
        customH3DPreview1.setLayout(customH3DPreview1Layout);
        customH3DPreview1Layout.setHorizontalGroup(
            customH3DPreview1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 189, Short.MAX_VALUE)
        );
        customH3DPreview1Layout.setVerticalGroup(
            customH3DPreview1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );

        areaW.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(headerSep, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(areaWLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(areaW, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(areaHLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(areaH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(uidLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(uid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(mdlLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(mdl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(dummy)
                                    .addComponent(item)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(shadow)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(shadowType, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(render)
                                    .addComponent(collLabel))
                                .addGap(8, 12, Short.MAX_VALUE)
                                .addComponent(listSep, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(entryCountLabel))))
                        .addComponent(customH3DPreview1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRemove, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(uidLabel)
                            .addComponent(uid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mdlLabel)
                            .addComponent(mdl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(entryCountLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(headerSep, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(render)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(shadow)
                            .addComponent(shadowType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(item)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dummy)
                        .addGap(20, 20, 20)
                        .addComponent(collLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(areaWLabel)
                            .addComponent(areaH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(areaHLabel)
                            .addComponent(areaW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(listSep, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(35, 35, 35)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(customH3DPreview1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemove)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
		saveEntry(false);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
		if (reg.entries.size() < 31) {
			NPCRegistry.NPCRegistryEntry e2 = new NPCRegistry.NPCRegistryEntry();
			reg.entries.put(e2.uid, e2);
			model.addElement("UID: " + e2.uid + " | Model: " + e2.model);
			dict.add(e2.uid);
			setEntry(e2.uid);
			reg.modified = true;
		} else {
			JOptionPane.showMessageDialog(this, "An area can hold a maximum of 31 NPCs.\n"
					+ "Reassign duplicates, free some space and try again.", "Registry limit error", JOptionPane.ERROR_MESSAGE);
		}
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
		int index = entryList.getSelectedIndex();
		reg.entries.remove(dict.get(index));
		model.removeElementAt(index);
		dict.remove(index);
		if (index >= model.size()) {
			entryList.setSelectedIndex(index - 1);
		} else {
			entryList.setSelectedIndex(index);
		}
		reg.modified = true;
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void shadowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shadowActionPerformed
		if (shadow.isSelected()) {
			if (e.shadowEnabled != 0) {
				shadowType.setValue(e.shadowEnabled);
			} else {
				shadowType.setValue(1);
			}
		} else {
			shadowType.setValue(0);
		}
    }//GEN-LAST:event_shadowActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(NPCRegistryEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new NPCRegistryEditor().setVisible(true);
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField areaH;
    private javax.swing.JLabel areaHLabel;
    private javax.swing.JFormattedTextField areaW;
    private javax.swing.JLabel areaWLabel;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel collLabel;
    private ctrmap.humaninterface.CustomH3DPreview customH3DPreview1;
    private javax.swing.JCheckBox dummy;
    private javax.swing.JLabel entryCountLabel;
    private javax.swing.JList<String> entryList;
    private javax.swing.JSeparator headerSep;
    private javax.swing.JCheckBox item;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator listSep;
    private javax.swing.JSpinner mdl;
    private javax.swing.JLabel mdlLabel;
    private javax.swing.JCheckBox render;
    private javax.swing.JCheckBox shadow;
    private javax.swing.JFormattedTextField shadowType;
    private javax.swing.JSpinner uid;
    private javax.swing.JLabel uidLabel;
    // End of variables declaration//GEN-END:variables
}
