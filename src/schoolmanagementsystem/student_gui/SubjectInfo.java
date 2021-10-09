/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem.student_gui;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import schoolmanagementsystem.Subject;
import schoolmanagementsystem.Student;
import schoolmanagementsystem.Session;
import schoolmanagementsystem.Note;
import schoolmanagementsystem.Professor;

/**
 *
 * @author japz3
 */
public class SubjectInfo extends javax.swing.JFrame {
    private Student student;
    private Subject subject;
    private Professor professor;
    private DefaultTableModel sessionModel;
    private DefaultTableModel notesModel;

    SubjectInfo(Subject subject, Student student) {
        this.initComponents();
        this.subject = subject;
        this.student = student;
        this.sessionModel = (DefaultTableModel) sessionsTable.getModel();
        this.notesModel = (DefaultTableModel) notesTable.getModel();
        
        this.loadProfessorData();
        this.subjectName.setText(subject.name);
        this.profName.setText(professor.name);
        
        SubjectInfo parent = this;
        
        sessionsTable.removeColumn(sessionsTable.getColumnModel().getColumn(2));
        sessionsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    StudentSessionView frame = new StudentSessionView(
                        (Session) sessionModel.getValueAt(sessionsTable.getSelectedRow(), 2),
                        parent.subject,
                        parent.student
                    );
                    
                    frame.setVisible(true);
                }
            }
        });
        
        notesTable.removeColumn(notesTable.getColumnModel().getColumn(2));
        notesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    
                    StudentNoteView frame = new StudentNoteView(
                        (Note) notesModel.getValueAt(notesTable.getSelectedRow(), 2),
                        parent.subject
                    );
                    
                    frame.setVisible(true);
                }
            }
        });
        
        this.loadData();
    }
    
    void loadProfessorData() {
        try {
            this.professor = this.subject.getProfessor();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
            this.dispose();
        }
    }
    
    void loadData() {
        this.loadSessionsToTable();
        this.loadNotesToTable();
    }
    
    void loadSessionsToTable() {
        try {
            sessionModel.setRowCount(0);
            Session[] sessions = this.subject.getSessions();
            for (int i = 0; i < sessions.length; i++) {
                Session session = sessions[i];
                Object[] field = {session.title, session.heldAt.get().toString(), session};
                sessionModel.addRow(field);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
            e.printStackTrace();
        }
    }
    
    void loadNotesToTable() {
        try {
            notesModel.setRowCount(0);
            Note[] notes = this.subject.getNotes();
            for (int i = 0; i < notes.length; i++) {
                Note note = notes[i];
                Object[] field = {note.title, note.updatedAt.get().toString(), note};
                notesModel.addRow(field);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        subjectName = new javax.swing.JLabel();
        profName = new javax.swing.JLabel();
        subjectContentTab = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sessionsTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        notesTable = new javax.swing.JTable();
        dropButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        subjectName.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        subjectName.setText("Subject Name");

        profName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        profName.setText("Professor Name");

        sessionsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title", "Posted At", "Data"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(sessionsTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        subjectContentTab.addTab("Sessions", jPanel1);

        notesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title", "Posted At", "Data"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(notesTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        subjectContentTab.addTab("Notes", jPanel2);

        dropButton.setText("Drop Subject");
        dropButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dropButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(subjectContentTab, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(profName)
                            .addComponent(subjectName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dropButton)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(subjectName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(profName))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(dropButton)))
                .addGap(18, 18, 18)
                .addComponent(subjectContentTab, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dropButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dropButtonActionPerformed
        String msg = String.format("Are you sure you want to delete this subject?\nPlease type \"%s\" to proceed.", subject.name);
        String name = JOptionPane.showInputDialog(rootPane, msg);
        try {
            if (name != null && name.equals(subject.name)) {
                this.student.dropSubject(subject);
                JOptionPane.showMessageDialog(rootPane, "Subject dropped successfully.");
                this.dispose();
            } else {
                throw new Exception("Cannot delete subject.");
            }  
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }//GEN-LAST:event_dropButtonActionPerformed

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
            java.util.logging.Logger.getLogger(SubjectInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SubjectInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SubjectInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SubjectInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dropButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable notesTable;
    private javax.swing.JLabel profName;
    private javax.swing.JTable sessionsTable;
    private javax.swing.JTabbedPane subjectContentTab;
    private javax.swing.JLabel subjectName;
    // End of variables declaration//GEN-END:variables
}
