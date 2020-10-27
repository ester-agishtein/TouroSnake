package touro.snake.strategy.astar;

import touro.snake.*;
import touro.snake.strategy.SnakeStrategy;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class AStarStrategy implements SnakeStrategy {
    Snake snake;
    Garden garden;
    @Override
    public void turnSnake(Snake snake, Garden garden) {
        this.snake = snake;
        this.garden = garden;
        Node startNode = new Node(snake.getHead());
        boolean isReady = false;
            while(!isReady) {
                if(garden.getFood() == null) return;
                else isReady = true;
            }
            Node endNode = new Node(garden.getFood());
            System.out.println("endNode = "+  endNode);
            createPath(startNode, endNode);

        //I think I'm misunderstanding something here. My path doesn't actually return anything. I think I need to change the snake in place somehow but can't figure that out.
        //Here is my theortical algortithem


    }

    public void createPath(Node startNode, Node endNode){
        //        Create start and end node
        ArrayList<Node> path = new ArrayList<>();


//     Initialize both open and closed list
        ArrayList<Node> openList = new ArrayList<>();
        ArrayList<Node> closedList = new ArrayList<>();

//     Add the start node
        openList.add(startNode);

        while(openList.size() > 0) {

//        Get the current node
            endNode = new Node(garden.getFood());
            Node currentNode = openList.get(0);
            int currentIndex = 0;
            for(int index = 0; index<openList.size(); index++) {
                Node item = openList.get(index);
                if( item.getCost() < currentNode.getCost()) {
                    currentNode = item;
                    currentIndex = index;
                }
            }
            System.out.println("currentNode = " + currentNode);
//        Pop current off open list, add to closed list
            openList.remove(currentIndex);
            closedList.add(currentNode);

//        Found the goal
            System.out.println("current Node = " + currentNode.getX() + " : " + currentNode.getY());
            System.out.println("end Node = " + endNode.getX() + " : " + endNode.getY());
            if( currentNode == endNode) {
                Node current;
                current = currentNode;
                while (current != null) {
                    System.out.println("current  = " + current);
                    path.add(current);
                    //Why is this returning blanks???
                    //its never reaching the goal....
                    System.out.println("Path after current added = " + path);
                    current = currentNode.getParent();
                }
            }
            System.out.println("reverse path = " + path);
            path = reverseArrayList(path);
//        Generate children
            ArrayList<Node> children = new ArrayList<>();
            HashMap<Direction,Node> directionNodeHashMap = findNeighbors();

            Node newNodePos = new Node(currentNode);
            for(Node neighbor: directionNodeHashMap.values()){
                if(neighbor.getCost() < newNodePos.getCost()){
                    newNodePos = neighbor;
                    Direction direction = Direction.North;
                    for(Map.Entry<Direction, Node> entry : directionNodeHashMap.entrySet()){
                        if(entry.getValue().equals(newNodePos)){
                            direction = entry.getKey();
                        }
                    }
                    snake.turnTo(direction);
                }
            }
            System.out.println("newNodePos = " + newNodePos.getX() + " : " + newNodePos.getY());
//            Make sure within range
                if (!newNodePos.inBounds()) continue;


//            Append
                children.add(newNodePos);
                System.out.println("children = " + children.toString());
//        Loop through children
            for(Node child : children) {

//            Child is on the closed list

                for (Node closedChild : closedList) {
                    if (child == closedChild) {
                        continue;
                    }
                }

//            Child is already in the open list
                for (Node openNode : openList) {
                    if (child == openNode && child.distance(startNode) > openNode.distance(startNode))
                        continue;
                }
//            Add the child to the open list
                openList.add(child);
                System.out.println("openList = " + openList);
            }
            System.out.println("path in function = "+ path);

        }

    }
    public ArrayList<Node> reverseArrayList(ArrayList<Node> alist)
    {
        // Arraylist for storing reversed elements
        ArrayList<Node> revArrayList = new ArrayList<Node>();
        for (int i = alist.size() - 1; i >= 0; i--) {

            // Append the elements in reverse order
            revArrayList.add(alist.get(i));
        }

        // Return the reversed arraylist
        return revArrayList;
    }

    public HashMap<Direction,Node> findNeighbors(){

        HashMap<Direction,Node> directionNodeHashMap = new HashMap<>();

        Node left = new Node(-1,-0);
        Node right = new Node(1,0);
        Node up = new Node(0,1);
        Node down = new Node(0,-1);

        //I don't know what to do with these fellas but I feel they're important.
        Node diDownLeft = new Node(-1,-1);
        Node diDownRight = new Node(-1, 1);
        Node diUpLeft = new Node(1, -1);
        Node diUpRight = new Node(1, 1);

        directionNodeHashMap.put(Direction.West, left);
        directionNodeHashMap.put(Direction.East, right);
        directionNodeHashMap.put(Direction.North, up);
        directionNodeHashMap.put(Direction.South, down);

        return directionNodeHashMap;
    }
}



