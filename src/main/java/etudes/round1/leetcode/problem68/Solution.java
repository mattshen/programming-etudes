package etudes.round1.leetcode.problem68;

import java.util.ArrayList;
import java.util.List;

class Element {
}

class Word extends Element {
    private String value;

    Word(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

class Space extends Element {
    public static final int MIN_SPACE = 1;
    private int count;

    Space(int count) {
        this.count = count;
    }

    public void increase() {
        this.count++;
    }

    public int getCount() {
        return count;
    }
}

class Line {
    private final int maxWidth;
    int currentWidth;
    private List<Element> elements = new ArrayList<>();

    Line(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public boolean attemptAddWord(String w) {
        if (canFit(w)) {
            if (currentWidth > 0) {
                elements.add(new Space(Space.MIN_SPACE));
                currentWidth += Space.MIN_SPACE;
            }
            elements.add(new Word(w));
            currentWidth += w.length();
            return true;
        } else {
            return false;
        }
    }

    public List<Space> getAllSpaces() {
        List<Space> spaces = new ArrayList<>();
        for (Element e : elements) {
            if (e instanceof Space) {
                spaces.add((Space) e);
            }
        }
        return spaces;
    }

    public Line stretchAll() {
        int roomToStretch = maxWidth - currentWidth;
        List<Space> spaces = getAllSpaces();
        int totalSpace = spaces.size();
        if (totalSpace > 0) {
            for (int i = 0; i < roomToStretch; i++) {
                spaces.get(i % totalSpace).increase();
            }
        } else {
            stretchRight();
        }
        return this;
    }

    public Line stretchRight() {
        int padding = maxWidth - currentWidth;
        Space pad = new Space(padding);
        this.elements.add(pad);
        return this;
    }

    private boolean canFit(String word) {
        if (currentWidth == 0) {
            return word.length() <= maxWidth;
        } else {
            return (currentWidth + Space.MIN_SPACE + word.length() <= maxWidth);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Element e : this.elements) {
            if (e instanceof Word) {
                sb.append(((Word) e).getValue());
            } else if (e instanceof Space) {
                for (int j = 0; j < ((Space) e).getCount(); j++) {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }
}

class Paragraph {
    private final int maxWidth;
    private List<Line> lines = new ArrayList<>();

    public Paragraph(int maxWidth) {
        this.maxWidth = maxWidth;
        this.lines.add(new Line(maxWidth));
    }

    public void take(String[] words) {
        for (String word : words) {
            Line line = this.lines.get(this.lines.size() - 1);
            if (!line.attemptAddWord(word)) {
                Line newLine = new Line(maxWidth);
                newLine.attemptAddWord(word);
                this.lines.add(newLine);
            }
        }
    }

    public void format() {
        for (int i = 0; i < lines.size() - 1; i++) {
            Line line = lines.get(i);
            line.stretchAll();
        }
        //last line
        lines.get(lines.size() - 1).stretchRight();
    }

    public List<String> yield() {
        List<String> strLines = new ArrayList<>();
        for (Line l : lines) {
            strLines.add(l.toString());
        }
        return strLines;
    }
}

public class Solution {

    public static void main(String[] args) {
        String[] words1 = new String[]{"This", "is", "an", "example", "of", "text", "justification."};
        String[] words2 =
                new String[]{"Science", "is", "what", "we", "understand", "well", "enough", "to", "explain",
                        "to", "a", "computer.", "Art", "is",
                        "everything", "else", "we", "do"};

        String[] words3 = new String[]{"What", "must", "be", "acknowledgment", "shall", "be"};
        String[] words4 = new String[]{"Listen", "to", "many,", "speak", "to", "a", "few."};
        int maxWidth = 6;
        List<String> lines = new Solution().fullJustify(words4, maxWidth);
        for (String line : lines) {
            System.out.println(line);
        }
    }


    public List<String> fullJustify(String[] words, int maxWidth) {
        Paragraph p = new Paragraph(maxWidth);
        p.take(words);
        p.format();
        return p.yield();
    }
}
