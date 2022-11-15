import java.io.IOException;

import com.google.googlejavaformat.java.FormatterException;
import com.zdpx.coder.SceneCodeBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public class Application {
    public static void main(String[] args) throws IOException, FormatterException {
        var configuration = "./flinkGraph/src/main/resources/general.scene.json";
        var scene = SceneCodeBuilder.readSceneFromFile(configuration);
        if (scene == null) {
            log.error("cannot find configure file: {}", configuration);
        }
        var sceneInternal = SceneCodeBuilder.convertToInternal(scene);
        SceneCodeBuilder su = new SceneCodeBuilder(sceneInternal);
        var result = su.build();
        System.out.println(result);
    }

}
