package touro.snake.strategy.astar;

import touro.snake.*;
import touro.snake.strategy.SnakeStrategy;

import java.util.*;

//
public class AStarStrategy implements SnakeStrategy {
    private Snake snake;
    private Square head;
    private Garden garden;
    private Direction directions[];
    private Food food;
    private List<Node> open;
    private List<Node> closed;
    private List<Node> path;
    @Override
    public void turnSnake(Snake snake, Garden garden) {
        this.snake = snake;
        this.garden = garden;
        this.head = snake.getHead();
        this.food = garden.getFood();
        this.directions =  Direction.values();
        this.open = new ArrayList<>();
        this.closed = new ArrayList<>();
        this.path = new ArrayList<>();


        if (food == null) {
            return;
        }


        Node firstNode = new Node(head);
        open.add(firstNode);

        while (!open.isEmpty()) {
            //Current node should be the node with the lowest cost
            Node current = getLowestCost(open);
            open.remove(current);
            closed.add(current);

            if (current.equals(food)) {
                this.moveToFood(current);
                break;
            }
            else {
                open = this.findPath(open, closed, current);
            }
        }
    }


    @Override
    public List<Square> getSearchSpace() {
        List<Node> joinedList = new ArrayList<>();
        joinedList.addAll(open);
        joinedList.addAll(closed);
        return getMarkedPath(joinedList);
    }

    @Override
    public List<Square> getPath() {
        return getMarkedPath(path);
    }

    private List<Square> getMarkedPath(List<Node> path){
        List<Square> currPath = new ArrayList<>();
        for (int index = 0; index< path.size(); index++) {

            if (snake.contains(path.get(index))) {
                continue;
            }
            if(food.equals(path.get(index))){
                continue;
            }

            Square sqr = new Square(path.get(index));
            currPath.add(sqr);
        }
        return currPath;
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
            this.path.add(n);
        }

        return n;
    }

    public void moveToFood(Node current){
        Node firstChild = getFirstChild(head, current);
        Direction direction = head.directionTo(firstChild);
        snake.turnTo(direction);
    }

    public List<Node> findPath (List<Node> open,List<Node> closed, Node current){
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
                    open.set(index,neighbor);
                }
            } else {
                open.add(neighbor);
            }

        }
        return open;
    }


}



