package com.mygdx.game;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class createObstacle {

    public createObstacle(Map map, World world)
    {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        ArrayList<Body> bodies = new ArrayList<Body>();
        int i = 0;

        for(MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) *3 / Constants.PPM, (rect.getY() + rect.getHeight() / 2)* 3 / Constants.PPM);
            bodies.add(world.createBody(bdef));
            shape.setAsBox((rect.getWidth() / 2) * 3/ Constants.PPM, (rect.getHeight() / 2) * 3/ Constants.PPM);
            fdef.shape = shape;
            bodies.get(i++).createFixture(fdef).setUserData(this);

        }
    }
}
