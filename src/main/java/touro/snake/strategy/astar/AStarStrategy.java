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
    private boolean mealAchieved;

    List<Node> snakePath = new ArrayList<>();
    @Override
    public void turnSnake(Snake snake, Garden garden) {
        this.snake = snake;
        this.garden = garden;
        this.head = snake.getHead();
        this.food = garden.getFood();
        this.directions =  Direction.values();
        this.mealAchieved = false;

        if (food == null) {
            return;
        }

        List<Node> open = new ArrayList<>();
        List<Node> closed = new ArrayList<>();

        Node firstNode = new Node(head);
        open.add(firstNode);
        snakePath.add(firstNode);

        while (!open.isEmpty()) {
            //Current node should be the node with the lowest cost
            Node current = getLowestCost(open);
            open.remove(current);
            closed.add(current);
            snakePath.add(current);

            if (current.equals(food)) {
                this.moveToFood(current);
                this.getSearchSpace();
                break;
            }
            else {
                open = this.findPath(open, closed, current);
            }
        }
    }



    @Override
    public List<Square> getSearchSpace() {
        List<Square> squareList = new ArrayList<>();
        if(this.mealAchieved){
            snakePath.clear();
            this.mealAchieved = false;
        }
        else {
            for (Iterator<Node> iterator = snakePath.iterator(); iterator.hasNext(); ) {
                Node nextNode = iterator.next();
                if (snake.contains(nextNode)) {
                    continue;
                }
                if(food.equals(nextNode)){
                    continue;
                }

                Square sqr = new Square(nextNode);
                squareList.add(sqr);
            }
        }

        return squareList;
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

    public void moveToFood(Node current){
        // TODO: we need to turn the snake with the correct path
//        mealAchieved = true;
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
                    snakePath.add(neighbor);
                }
            } else {
                open.add(neighbor);
            }

        }
        return open;
    }


}



