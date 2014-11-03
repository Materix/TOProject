
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainFrame extends JFrame {

	private static final int DEFAULT_WIDTH = 200;
	private static final int DEFAULT_HEIGHT= 200;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MainFrame(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//setSize(600, 600);
		//setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		
		
		LoginPanel loginPanel= new LoginPanel();
		setContentPane(loginPanel);
		setMinimumSize(loginPanel.getMinimumSize());

//		GameList gameList= new GameList();
//		setContentPane(gameList);
//		setMinimumSize(gameList.getMinimumSize());

		
		//RoomView roomView= new RoomView();
		//setContentPane(roomView);
		//setMinimumSize(roomView.getMinimumSize());
		
//		GameView gameView= new GameView();
//		setContentPane(gameView);
//		setMinimumSize(gameView.getMinimumSize());
		
//		CreateGameView createGameView= new CreateGameView();
//		setContentPane(createGameView);
//		setMinimumSize(createGameView.getMinimumSize());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Ko�ci");
		setIconImage((new ImageIcon("kosc.png")).getImage());
		//pack();
	}
}
