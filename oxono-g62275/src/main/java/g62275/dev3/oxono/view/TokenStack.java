package g62275.dev3.oxono.view;

import g62275.dev3.oxono.model.Color;
import g62275.dev3.oxono.model.Symbol;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class TokenStack extends StackPane {

    private Pane tokenStack;
    private Symbol symbol;
    private Color color;

    public TokenStack(Color color, Symbol symbol) {
        this.color = color;
        this.symbol = symbol;
        this.tokenStack = new Pane();

        for (int i = 0; i < 8; i++) {
            TokenView token = new TokenView(symbol, color);
            token.setTranslateY(-i * 20);
            tokenStack.getChildren().add(token);
        }

        this.getChildren().addAll(tokenStack);
    }

    public void removeToken() {
        if (!tokenStack.getChildren().isEmpty()) {
            tokenStack.getChildren().remove(tokenStack.getChildren().size() - 1);
        }
    }

    public void addToken() {
        if (tokenStack.getChildren().size() < 8) {
            TokenView token = new TokenView(symbol, color);
            token.setTranslateY(-tokenStack.getChildren().size() * 20);
            tokenStack.getChildren().add(token);
        }
    }
}
