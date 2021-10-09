package schoolmanagementsystem.admin_gui;

import schoolmanagementsystem.common_gui.SubjectEditor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import schoolmanagementsystem.Admin;
import database.Database;
import database.Table;
import schoolmanagementsystem.Professor;
import schoolmanagementsystem.Student;
import schoolmanagementsystem.Subject;

public class AdminDashboard extends javax.swing.JFrame {

    DefaultTableModel tableModel;
    String dataType = "admin";

    public AdminDashboard() {
        initComponents();
        tableModel = (DefaultTableModel) dataTable.getModel();
        loadData();

        dataTable.removeColumn(dataTable.getColumnModel().getColumn(2));
        dataTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                deleteButton.setEnabled(dataTable.getSelectedRow() != -1);
                
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    openEditor(tableModel.getValueAt(dataTable.getSelectedRow(), 2));
                }
            }
        });
    }

    void loadData() {
        try {
            deleteButton.setEnabled(false);
            
            tableModel.setRowCount(0);
            switch (dataType) {
                case "admin":
                    Admin[] admins = Admin.getAll();
                    for (int i = 0; i < admins.length; i++) {
                        Admin admin = admins[i];
                        Object[] field = {Long.toString(admin.id), admin.username, admin};
                        tableModel.addRow(field);
                    }
                    break;
                case "student":
                    Student[] students = Student.getAll();
                    for (int i = 0; i < students.length; i++) {
                        Student student = students[i];
                        Object[] field = {Long.toString(student.id), student.name, student};
                        tableModel.addRow(field);
                    }
                    break;
                case "professor":
                    Professor[] professors = Professor.getAll();
                    for (int i = 0; i < professors.length; i++) {
                        Professor professor = professors[i];
                        Object[] field = {Long.toString(professor.id), professor.name, professor};
                        tableModel.addRow(field);
                    }
                    break;
                case "subject":
                    Subject[] subjects = Subject.getAll();
                    for (int i = 0; i < subjects.length; i++) {
                        Subject subject = subjects[i];
                        Object[] field = {subject.id, subject.name, subject};
                        tableModel.addRow(field);
                    }
                    break;
                default:
                    throw new Exception(String.format("Unable to load data %s", this.dataType));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            dataType = "admin";
            loadData();
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        adminButton = new javax.swing.JButton();
        subjButton = new javax.swing.JButton();
        studButton = new javax.swing.JButton();
        profButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        logOutBTN = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        adminButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        adminButton.setText("Admins");
        adminButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminButtonActionPerformed(evt);
            }
        });

        subjButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        subjButton.setText("Subjects");
        subjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subjButtonActionPerformed(evt);
            }
        });

        studButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        studButton.setText("Students");
        studButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studButtonActionPerformed(evt);
            }
        });

        profButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        profButton.setText("Professors");
        profButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profButtonActionPerformed(evt);
            }
        });

        deleteButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        deleteButton.setText("DELETE");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        addButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addButton.setText("ADD");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        logOutBTN.setText("LOG OUT");
        logOutBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutBTNActionPerformed(evt);
            }
        });

        dataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Entry", "Data"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(dataTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(adminButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(subjButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(studButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(profButton, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(deleteButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logOutBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(logOutBTN)
                    .addComponent(addButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(adminButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(subjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(studButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(profButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void adminButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminButtonActionPerformed
        this.dataType = "admin";
        this.loadData();
    }//GEN-LAST:event_adminButtonActionPerformed

    private void subjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subjButtonActionPerformed
        this.dataType = "subject";
        this.loadData();
    }//GEN-LAST:event_subjButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
//        tableModel.getValueAt(dataTable.getSelectedRow(), 2)\
        String dataName = (String) tableModel.getValueAt(dataTable.getSelectedRow(), 1);
        int confirmDelete = JOptionPane.showConfirmDialog(rootPane, String.format("You are about to delete %s, are you sure you want to delete this?", dataName));
        if (confirmDelete == JOptionPane.YES_OPTION) {
            try {
                Table table = Database.get(dataType + "s");
                switch (dataType) {
                    case "admin":
                        Admin adminData = (Admin) tableModel.getValueAt(dataTable.getSelectedRow(), 2);
                        adminData.remove();
                        break;
                    case "student":
                        Student studentData = (Student) tableModel.getValueAt(dataTable.getSelectedRow(), 2);
                        studentData.remove();
                        break;
                    case "professor":
                        Professor profData = (Professor) tableModel.getValueAt(dataTable.getSelectedRow(), 2);
                        profData.remove();
                        break;
                    case "subject":
                        Subject subData = (Subject) tableModel.getValueAt(dataTable.getSelectedRow(), 2);
                        subData.remove();
                        break;
                }
                
                this.loadData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, e.getMessage());
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        openEditor(null);
    }//GEN-LAST:event_addButtonActionPerformed

    void openEditor(Object data) {
        JFrame frame;
        switch (dataType) {
            case "admin":
                frame = new AdminEditor((Admin) data);
                break;
            case "student":
                frame = new StudentEditor((Student) data);
                break;
            case "professor":
                frame = new ProfessorEditor((Professor) data);
                break;
            case "subject":
                frame = new SubjectEditor((Subject) data);
                break;
            default:
                return;
        }

        AdminDashboard dash = this;
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent arg0) {
                dash.loadData();
                // save the data
            }
        });

        frame.setVisible(true);
    }

    private void logOutBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutBTNActionPerformed

        try {
            JOptionPane.showMessageDialog(null, "Successfully Logged Out!");
            AdminLogin frame = new AdminLogin();
            frame.setVisible(true);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }//GEN-LAST:event_logOutBTNActionPerformed

    private void studButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studButtonActionPerformed
        this.dataType = "student";
        this.loadData();
    }//GEN-LAST:event_studButtonActionPerformed

    private void profButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profButtonActionPerformed
        this.dataType = "professor";
        this.loadData();
    }//GEN-LAST:event_profButtonActionPerformed

    public static void main(String args[]) {

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
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton adminButton;
    private javax.swing.JTable dataTable;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editBTN;
    private javax.swing.JButton editBTN1;
    private javax.swing.JButton editBTN2;
    private javax.swing.JButton editBTN3;
    private javax.swing.JButton editBTN4;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton logOutBTN;
    private javax.swing.JButton profButton;
    private javax.swing.JButton studButton;
    private javax.swing.JButton subjButton;
    // End of variables declaration//GEN-END:variables
}
