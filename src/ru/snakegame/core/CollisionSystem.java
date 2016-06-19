package ru.snakegame.core;

import ru.snakegame.core.math.Vector2;

/**
 * Author: Юрий
 * Created: 26.05.2016
 * Description:
 */
public class CollisionSystem {
    private static CollisionSystem ourInstance = new CollisionSystem();
    private CollisionResult isCollision = CollisionResult.NO_COLLISION;
    private Vector2<Integer> borders;
    public static CollisionSystem getInstance() {
        return ourInstance;
    }

    private CollisionSystem() {
    }

    public void setBorders(final Vector2<Integer> borders) {
        this.borders = borders;
    }

    public void setIsCollision(final CollisionResult collision) {
        this.isCollision = collision;
    }

    public CollisionResult isBorderCollision(final Vector2<Integer> snakeHead) {

        if ((snakeHead.getX() < 0) || (snakeHead.getY() < 0) ||
                (snakeHead.getX() > this.borders.getX() || snakeHead.getY() > this.borders.getY())) {

            this.setIsCollision(CollisionResult.BORDER_COLLISION);
            return CollisionResult.BORDER_COLLISION;
        }
        return CollisionResult.NO_COLLISION;
    }

    public CollisionResult isSnakeCollision(final Snake snake) {
        if (snake.isBelong(snake.getNextHead())) {
            this.setIsCollision(CollisionResult.SNAKE_COLLISION);
            return CollisionResult.SNAKE_COLLISION;
        }
        return CollisionResult.NO_COLLISION;
    }

    public CollisionResult isCollision(final FieldArray map, final Vector2<Integer> snakeHead) {
        if (isCollision != CollisionResult.NO_COLLISION) {
            return this.isCollision;
        }

        if (this.isBorderCollision(snakeHead) != CollisionResult.BORDER_COLLISION) {
            CellState cell = map.getCell(snakeHead).getState();
            switch (cell) {
                case CELL_APPLE:
                    return CollisionResult.APPLE_COLLISION;
                case CELL_SNAKE:
                    this.setIsCollision(CollisionResult.SNAKE_COLLISION);
                    return CollisionResult.SNAKE_COLLISION;
                default:
                    this.setIsCollision(CollisionResult.NO_COLLISION);
                    return CollisionResult.NO_COLLISION;
            }
        } else {
            return CollisionResult.BORDER_COLLISION;
        }
    }
}