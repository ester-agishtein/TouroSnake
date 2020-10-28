package touro.snake.strategy.astar;

import touro.snake.*;
import touro.snake.strategy.SnakeStrategy;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

//
public class AStarStrategy implements SnakeStrategy {

    @Override
    public void turnSnake(Snake snake, Garden garden) {
        Direction directions[] = Direction.values();
        Food food = garden.getFood();
        Square head = snake.getHead();
        if (food == null) {
            return;
        }

        List<Node> open = new ArrayList<>();
        List<Node> closed = new ArrayList<>();

        open.add(new Node(snake.getHead()));

        while (!open.isEmpty()) {
            //Current node should be the node with the lowest cost
            Node current = getLowestCost(open);
            open.remove(current);
            closed.add(current);
            if (current.equals(food)) {
                // TODO: we need to turn the snake with the correct path
                Node firstChild = getFirstChild(head, current);
                Direction direction = head.directionTo(firstChild);
                snake.turnTo(direction);
                return;
            }

            for (Direction direction : directions) {
                Node neighbor = new Node(current.moveTo(direction), current, food);
                if (!neighbor.inBounds() ||
                        snake.contains(neighbor) || closed.contains(neighbor)) {
                    continue;
                }

                //if you have a bunch of 54s, search for the one with the shortest path
                //parent includes path from start node
                if (open.contains(neighbor)) {
                    int index = open.indexOf(neighbor);
                    Node oldNeighbor = open.get(index);
                    if (neighbor.getCost() < oldNeighbor.getCost()) {
                        open.remove(index);
                        open.add(neighbor);

                    }
                } else {
                    open.add(neighbor);
                }

            }
        }
    }


    private Node getLowestCost(List<Node> nodes) {
        //use comparator = interface with a compare to object
        //this compares each node by its getCost and then returns the node
        return nodes.stream()
                .min(Comparator.comparingDouble(Node::getCost))
                .get();
    }

    public Node getFirstChild(Square head, Node end) {
        Node n = end;
        while (!n.getParent().equals(head)) {
            n = n.getParent();
        }
        return n;
    }

}



