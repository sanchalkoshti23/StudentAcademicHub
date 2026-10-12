
package studenthub.gui;

import javax.swing.SwingUtilities;

public class MainFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainDashboard::new);
    }
}
