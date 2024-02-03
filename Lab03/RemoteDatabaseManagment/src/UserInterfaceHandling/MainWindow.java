package UserInterfaceHandling;

import Entities.Employee;
import RemoteConnectionHandling.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class MainWindow extends JFrame {

    private JTextField idField;
    private JTextField nameField;
    private DefaultTableModel employeesTableModel;
    private RemoteConnectionHandling.Client c;

    MainWindow() {
        setTitle("Remote Database Management");
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(720, 320));
        setMaximumSize(new Dimension(720, 320));
        setLayout(new GridLayout(1,2));

        Font globalFont = new Font("Lucida Grande", Font.BOLD, 13);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridBagLayout());
        idField = new JTextField();
        idField.setPreferredSize(new Dimension(64, 32));
        idField.setFont(globalFont);

        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(128, 32));
        nameField.setFont(globalFont);

        JPanel textPanel = new JPanel();
        textPanel.add(idField);
        textPanel.add(nameField);

        JButton addButton = new JButton("Add");
        addButton.setFont(globalFont);
        addButton.setPreferredSize(new Dimension(128, 32));
        addButton.addActionListener(new AddActionListener());

        JButton updateButton = new JButton("Update");
        updateButton.setFont(globalFont);
        updateButton.setPreferredSize(new Dimension(128, 32));
        updateButton.addActionListener(new UpdateActionListener());

        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(globalFont);
        deleteButton.setPreferredSize(new Dimension(128, 32));
        deleteButton.addActionListener(new DeleteActionListener());

        JButton connectButton = new JButton("Connect");
        connectButton.setFont(globalFont);
        connectButton.setPreferredSize(new Dimension(128, 32));
        connectButton.addActionListener(new ConnectActionListener());

        GridBagConstraints hgbc = new GridBagConstraints();

        hgbc.gridwidth = 1;
        hgbc.gridheight = 5;
        hgbc.gridx = 0;
        hgbc.gridy = 0;
        headerPanel.add(textPanel, hgbc);

        hgbc.gridwidth = 1;
        hgbc.gridheight = 5;
        hgbc.gridx = 0;
        hgbc.gridy = 5;
        hgbc.ipadx = 100;
        hgbc.ipady = 5;
        headerPanel.add(addButton, hgbc);

        hgbc.gridwidth = 1;
        hgbc.gridheight = 5;
        hgbc.gridx = 0;
        hgbc.gridy = 10;
        headerPanel.add(updateButton, hgbc);

        hgbc.gridwidth = 1;
        hgbc.gridheight = 5;
        hgbc.gridx = 0;
        hgbc.gridy = 15;
        headerPanel.add(deleteButton, hgbc);

        hgbc.gridwidth = 1;
        hgbc.gridheight = 5;
        hgbc.gridx = 0;
        hgbc.gridy = 20;
        headerPanel.add(connectButton, hgbc);

        JPanel tablePanel = new JPanel(new GridBagLayout());

        JTable employeesTable = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        employeesTableModel = new DefaultTableModel(new String[]{"ID", "Name"}, 0);
        employeesTable.setModel(employeesTableModel);

        JScrollPane tableContainer = new JScrollPane(employeesTable);
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(globalFont);
        refreshButton.setPreferredSize(new Dimension(128, 32));
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (c != null) {
                    try {
                       employeesTableModel.setDataVector(c.getEmployees(), new String[]{"ID", "Name"});
                    } catch (Exception ex) {
                        refreshError();
                    }
                }
            }
            private void refreshError() {
                JOptionPane.showMessageDialog(MainWindow.this
                        , "Error retrieving employees!\nMake sure that connection is established", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridheight = 10;
        gbc.gridwidth = 10;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 300;
        gbc.ipady = 200;
        tablePanel.add(tableContainer, gbc);

        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridy = 10;
        gbc.ipadx = 250;
        gbc.ipady = 5;
        tablePanel.add(refreshButton, gbc);

        add(headerPanel);
        add(tablePanel);
        setVisible(true);
    }
    private void refresh() {
        if (c != null) {
            try {
                employeesTableModel.setDataVector(c.getEmployees(), new String[]{"ID", "Name"});
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private class AddActionListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        public void actionPerformed(ActionEvent e) {
            try {
                int ID = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                Employee employee = new Employee(ID, name);
                if (c.add(employee)) {
                    JOptionPane.showMessageDialog(MainWindow.this
                            , "Success!", "Operation Done",
                            JOptionPane.INFORMATION_MESSAGE);
                    refresh();
                } else addError();
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(MainWindow.this
                        , "ID invalid!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } catch (RemoteException ex) {
                addError();
                ex.printStackTrace();
            } catch (Exception ex) {
                addError();
            }
        }
        private void addError() {
            JOptionPane.showMessageDialog(MainWindow.this
                    , "Error adding employee!\nMake sure that connection is established", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private class UpdateActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            try {
            int ID = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            Employee employee = new Employee(ID, name);
            if (c.update(ID, employee)) {
                JOptionPane.showMessageDialog(MainWindow.this
                        , "Success!", "Operation Done",
                        JOptionPane.INFORMATION_MESSAGE);
                refresh();
            } else updateError();
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(MainWindow.this
                    , "ID invalid!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } catch (RemoteException ex) {
            updateError();
            ex.printStackTrace();
        } catch (Exception ex) {
            updateError();
        }
    }
    private void updateError() {
        JOptionPane.showMessageDialog(MainWindow.this
                , "Error updating employee!\nMake sure that connection is established", "Error",
                JOptionPane.ERROR_MESSAGE);
    }
    }

    private class DeleteActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int ID = Integer.parseInt(idField.getText());
                if (c.delete(ID)) {
                    JOptionPane.showMessageDialog(MainWindow.this
                            , "Success!", "Operation Done",
                            JOptionPane.INFORMATION_MESSAGE);
                    refresh();
                } else deleteError();
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(MainWindow.this
                        , "ID invalid!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } catch (RemoteException ex) {
                deleteError();
                ex.printStackTrace();
            } catch (Exception ex) {
                deleteError();
            }
        }
        private void deleteError() {
            JOptionPane.showMessageDialog(MainWindow.this
                    , "Error deleting employee!\nMake sure that connection is established", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private class ConnectActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                c = new Client();
                JOptionPane.showMessageDialog(MainWindow.this
                        , "Connection Success!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                refresh();
            } catch (RemoteException | NotBoundException ex) {
                JOptionPane.showMessageDialog(MainWindow.this
                        , "Connection Error!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
