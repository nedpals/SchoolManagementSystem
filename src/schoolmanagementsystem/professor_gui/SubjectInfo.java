/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem.professor_gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import schoolmanagementsystem.Note;
import schoolmanagementsystem.Professor;
import schoolmanagementsystem.Session;
import schoolmanagementsystem.Subject;
import schoolmanagementsystem.Student;
import schoolmanagementsystem.common_gui.SubjectEditor;

/**
 *
 * @author User
 */
public class SubjectInfo extends javax.swing.JFrame {

    Professor professor;
    Subject subject;
    private DefaultTableModel sessionModel;
    private DefaultTableModel notesModel;
    private DefaultTableModel studentsModel;

    /**
     * Creates new form subjectWindow
     */
    public SubjectInfo(Subject subject, Professor prof) {
        initComponents();

        this.sessionModel = (DefaultTableModel) sessionsTable.getModel();
        this.notesModel = (DefaultTableModel) notesTable.getModel();
        this.studentsModel = (DefaultTableModel) studentsTable.getModel();
        this.subject = subject;
        this.professor = prof;

        this.subjectName.setText(subject.name);

        SubjectInfo parent = this;

        sessionsTable.removeColumn(sessionsTable.getColumnModel().getColumn(2));
        sessionsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();

                    SessionEditor editor = new SessionEditor((Session) sessionModel.getValueAt(sessionsTable.getSelectedRow(), 2));
                    editor.setVisible(true);
//                    openEditor(tableModel.getValueAt(subjectTable.getSelectedRow(), 2));
                }
            }
        });

        notesTable.removeColumn(notesTable.getColumnModel().getColumn(2));
        notesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
//                    openEditor(tableModel.getValueAt(subjectTable.getSelectedRow(), 2));
                    NoteEditor editor = new NoteEditor((Note) notesModel.getValueAt(notesTable.getSelectedRow(), 2));
                    editor.setVisible(true);
                }
            }
        });
//        
        studentsTable.removeColumn(studentsTable.getColumnModel().getColumn(2));
        studentsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    
                    Student selected = (Student) studentsModel.getValueAt(studentsTable.getSelectedRow(), 2);
                    int confirm = JOptionPane.showConfirmDialog(rootPane, String.format("Do you want to remove %s?", selected.name));
                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            JOptionPane.showMessageDialog(rootPane, "Student removed successfully.");
                            selected.dropSubject(parent.subject);
                            parent.loadData();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
//                    openEditor(tableModel.getValueAt(subjectTable.getSelectedRow(), 2));
                }
            }
        });

        loadData();
    }

    void loadData() {
        this.loadSessionsToTable();
        this.loadNotesToTable();
        this.loadStudentsToTable();
    }

    void loadSessionsToTable() {
        try {
            sessionModel.setRowCount(0);
            Session[] sessions = this.subject.getSessions();
            for (int i = 0; i < sessions.length; i++) {
                Session session = sessions[i];
                if (session == null) {
                    continue;
                }
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
                if (note == null) {
                    continue;
                }
                Object[] field = {note.title, note.updatedAt.get().toString(), note};
                notesModel.addRow(field);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
            e.printStackTrace();
        }
    }

    void loadStudentsToTable() {
        try {
            studentsModel.setRowCount(0);
            Student[] students = this.subject.getStudents();
            for (int i = 0; i < students.length; i++) {
                Student stud = students[i];
                if (stud == null) {
                    continue;
                }
                Object[] field = {stud.name, stud.section, stud};
                studentsModel.addRow(field);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        subjectName = new javax.swing.JLabel();
        editSubjButton = new javax.swing.JButton();
        deleteSubjButton = new javax.swing.JButton();
        profSubjectInfoTabs = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        addSessionsBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        sessionsTable = new javax.swing.JTable();
        refreshSessionsBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        addNotesBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        notesTable = new javax.swing.JTable();
        refreshNotesBtn = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        addStudentButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        studentsTable = new javax.swing.JTable();
        refreshStudentsBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        subjectName.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        subjectName.setText("Subject Name");

        editSubjButton.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        editSubjButton.setText("Edit Subject");
        editSubjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editSubjButtonActionPerformed(evt);
            }
        });

        deleteSubjButton.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        deleteSubjButton.setText("Delete Subject");
        deleteSubjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSubjButtonActionPerformed(evt);
            }
        });

        addSessionsBtn.setText("Add Sessions");
        addSessionsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSessionsBtnActionPerformed(evt);
            }
        });

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
        jScrollPane2.setViewportView(sessionsTable);

        refreshSessionsBtn.setText("Refresh");
        refreshSessionsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshSessionsBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(refreshSessionsBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addSessionsBtn))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addSessionsBtn)
                    .addComponent(refreshSessionsBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE))
        );

        profSubjectInfoTabs.addTab("Sessions", jPanel2);

        addNotesBtn.setText("Add Notes");
        addNotesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNotesBtnActionPerformed(evt);
            }
        });

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
        jScrollPane3.setViewportView(notesTable);

        refreshNotesBtn.setText("Refresh");
        refreshNotesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshNotesBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(refreshNotesBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addNotesBtn))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addNotesBtn)
                    .addComponent(refreshNotesBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE))
        );

        profSubjectInfoTabs.addTab("Notes", jPanel3);

        addStudentButton.setText("Add Student");
        addStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStudentButtonActionPerformed(evt);
            }
        });

        studentsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Name", "Section", "Data"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(studentsTable);

        refreshStudentsBtn.setText("Refresh");
        refreshStudentsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshStudentsBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(388, 388, 388)
                .addComponent(refreshStudentsBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addStudentButton))
            .addComponent(jScrollPane4)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addStudentButton)
                    .addComponent(refreshStudentsBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE))
        );

        profSubjectInfoTabs.addTab("Students", jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(profSubjectInfoTabs)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(subjectName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editSubjButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteSubjButton)))
                .addGap(25, 25, 25))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subjectName)
                    .addComponent(editSubjButton)
                    .addComponent(deleteSubjButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(profSubjectInfoTabs, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deleteSubjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSubjButtonActionPerformed
        String msg = String.format("Are you sure you want to delete this subject?\nPlease type \"%s\" to proceed.", subject.name);
        String name = JOptionPane.showInputDialog(rootPane, msg);
        try {
            if (name != null && name.equals(subject.name)) {
                this.subject.remove();
                JOptionPane.showMessageDialog(rootPane, "Subject deleted successfully.");
                this.dispose();
            } else {
                throw new Exception("Cannot delete subject.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }//GEN-LAST:event_deleteSubjButtonActionPerformed

    private void editSubjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editSubjButtonActionPerformed
        SubjectEditor frame = new SubjectEditor(subject, professor.name);
        SubjectInfo parent = this;
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent arg0) {
                parent.subjectName.setText(subject.name);
                // save the data
            }
        });
        frame.setEnabled(true);
    }//GEN-LAST:event_editSubjButtonActionPerformed

    private void addSessionsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSessionsBtnActionPerformed
        SessionEditor editor = new SessionEditor(subject);
        editor.setVisible(true);
    }//GEN-LAST:event_addSessionsBtnActionPerformed

    private void addNotesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNotesBtnActionPerformed
        NoteEditor editor = new NoteEditor(subject);
        editor.setVisible(true);
    }//GEN-LAST:event_addNotesBtnActionPerformed

    private void refreshSessionsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshSessionsBtnActionPerformed
        loadSessionsToTable();
    }//GEN-LAST:event_refreshSessionsBtnActionPerformed

    private void refreshNotesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshNotesBtnActionPerformed
        loadNotesToTable();
    }//GEN-LAST:event_refreshNotesBtnActionPerformed

    private void refreshStudentsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshStudentsBtnActionPerformed
        loadStudentsToTable();
    }//GEN-LAST:event_refreshStudentsBtnActionPerformed

    private void addStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStudentButtonActionPerformed
        AddStudentSearch frame = new AddStudentSearch(this.subject);
        frame.setVisible(true);
        SubjectInfo dash = this;
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent arg0) {
                dash.loadData();
            }
        });      
    }//GEN-LAST:event_addStudentButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addNotesBtn;
    private javax.swing.JButton addSessionsBtn;
    private javax.swing.JButton addStudentButton;
    private javax.swing.JButton deleteSubjButton;
    private javax.swing.JButton editSubjButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable notesTable;
    private javax.swing.JTabbedPane profSubjectInfoTabs;
    private javax.swing.JButton refreshNotesBtn;
    private javax.swing.JButton refreshSessionsBtn;
    private javax.swing.JButton refreshStudentsBtn;
    private javax.swing.JTable sessionsTable;
    private javax.swing.JTable studentsTable;
    private javax.swing.JLabel subjectName;
    // End of variables declaration//GEN-END:variables
}
