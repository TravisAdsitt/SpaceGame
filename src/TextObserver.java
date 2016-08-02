import java.util.Observable;
import java.util.Observer;


public class TextObserver implements Observer{

	
	public TextObserver(Model model){
		model.addObserver(this);
	}
	@Override
	public void update(Observable obs, Object obj) {
		System.out.println(obj);
		System.out.println("Got something!");
	}

}
