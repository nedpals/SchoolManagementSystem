/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem.student_gui;
import schoolmanagementsystem.student_gui.SubjectView;
import schoolmanagementsystem.student_gui.NotsView;

/**
 *
 * @author japz3
 */
public class NotesView extends javax.swing.JFrame {

    /**
     * Creates new form NotesView
     */
    public NotesView() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        stud_noteViewSubLabel = new javax.swing.JLabel();
        stud_noteViewProfLabel = new javax.swing.JLabel();
        stud_noteViewSessButton = new javax.swing.JButton();
        stud_noteViewNoteButton = new javax.swing.JButton();
        nScrollPane1 = new javax.swing.JScrollPane();
        nTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        stud_noteViewSubLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        stud_noteViewSubLabel.setText("Subject Name");

        stud_noteViewProfLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        stud_noteViewProfLabel.setText("Professor Name");

        stud_noteViewSessButton.setText("Sessions");
        stud_noteViewSessButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stud_noteViewSessButtonActionPerformed(evt);
            }
        });

        stud_noteViewNoteButton.setText("Notes");
        stud_noteViewNoteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stud_noteViewNoteButtonActionPerformed(evt);
            }
        });

        nTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "", ""
            }
        ));
        nScrollPane1.setViewportView(nTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(stud_noteViewSubLabel)
                            .addComponent(stud_noteViewProfLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(stud_noteViewSessButton)
                                .addGap(31, 31, 31)
                                .addComponent(stud_noteViewNoteButton)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(stud_noteViewSubLabel)
                .addGap(18, 18, 18)
                .addComponent(stud_noteViewProfLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stud_noteViewSessButton)
                    .addComponent(stud_noteViewNoteButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void stud_noteViewSessButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stud_noteViewSessButtonActionPerformed
        // TODO add your handling code here:
        SubjectView subview = new SubjectView();
         subview.setLocationRelativeTo(this);
        subview.setVisible(true);
         this.dispose();
    }//GEN-LAST:event_stud_noteViewSessButtonActionPerformed

    private void stud_noteViewNoteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stud_noteViewNoteButtonActionPerformed
        // TODO add your handling code here:
        NotsView notsv = new NotsView();
        notsv.setLocationRelativeTo(this);
        notsv.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_stud_noteViewNoteButtonActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NotesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NotesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NotesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NotesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NotesView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane nScrollPane1;
    private javax.swing.JTable nTable1;
    private javax.swing.JButton stud_noteViewNoteButton;
    private javax.swing.JLabel stud_noteViewProfLabel;
    private javax.swing.JButton stud_noteViewSessButton;
    private javax.swing.JLabel stud_noteViewSubLabel;
    // End of variables declaration//GEN-END:variables
}