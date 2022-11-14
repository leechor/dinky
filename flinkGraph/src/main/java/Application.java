import java.io.IOException;

import com.google.googlejavaformat.java.FormatterException;

import lombok.extern.slf4j.Slf4j;
import com.zdpx.coder.SceneCodeBuilder;

/**
 *
 */
@Slf4j
public class Application {
    public static void main(String[] args) throws IOException, FormatterException {
        var configuration = "D:\\project\\dlink\\flinkGraph\\src\\main\\resources\\general.scene.json";
        var scene = SceneCodeBuilder.readSceneFromFile(configuration);
        if (scene == null) {
            log.error("cannot find configure file: {}", configuration);
        }
        var sceneInternal = SceneCodeBuilder.convertToInternal(scene);
        SceneCodeBuilder su = new SceneCodeBuilder(sceneInternal);
        su.build();
    }

}
