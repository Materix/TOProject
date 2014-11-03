import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


public class CreateGameView extends JPanel {

	private static final long serialVersionUID = 6952287105002870473L;
	
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT= 300;

	public CreateGameView(JFrame mainFrame) {
		super();
		this.setLayout(new MigLayout("", "[grow][100:100:250]", ""));
		this.add(new JLabel("Tworzenie gry"), "cell 0 0 2 1,alignx center");
		this.add(new JLabel("Typ gry"), "cell 0 1,alignx left");
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(GameType.values()));
		add(comboBox, "cell 1 1,growx");
		this.add(new JLabel("Ilo�� �ywych graczy"), "cell 0 2, left");
		
		JSpinner spinner_3 = new JSpinner();
		add(spinner_3, "cell 1 2,growx,aligny center");
		this.add(new JLabel("Ilo�� s�abych bot�w"), "cell 0 3, left");
		
		JSpinner spinner_2 = new JSpinner();
		add(spinner_2, "cell 1 3,growx,aligny center");
		this.add(new JLabel("Ilo�� silnych bot�w"), "cell 0 4, left");
		
		JSpinner spinner_1 = new JSpinner();
		add(spinner_1, "cell 1 4,growx,aligny center");
		this.add(new JLabel("Ilo�� wygranych rund"), "cell 0 5, left");
		
		JSpinner spinner = new JSpinner();
		add(spinner, "cell 1 5,growx,aligny center");
		this.add(new JLabel("Tylko AI (ogl�daj gr�)"), "cell 0 6, left");
		
		JCheckBox tylkoAIcheckButton = new JCheckBox("");
		add(tylkoAIcheckButton, "cell 1 6,alignx center,aligny center");
		JButton create = new JButton("Stw�rz gr�");
		create.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RoomView roomView = new RoomView(mainFrame);
				mainFrame.setContentPane(roomView);
				mainFrame.setMinimumSize(roomView.getMinimumSize());
				mainFrame.setLocationRelativeTo(null);
				roomView.revalidate();
			}
		});
		this.add(create, "cell 1 10, left, w 100!");
		JButton back = new JButton("Wyjd�");
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GameList gameList = new GameList(mainFrame);
				mainFrame.setContentPane(gameList);
				mainFrame.setMinimumSize(gameList.getMinimumSize());
				mainFrame.setLocationRelativeTo(null);
				mainFrame.revalidate();
			}
		});
		this.add(back, "cell 0 10, right, w 100!");
		
		
		
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
	}
}
