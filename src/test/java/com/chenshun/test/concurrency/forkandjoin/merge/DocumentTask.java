package com.chenshun.test.concurrency.forkandjoin.merge;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

/**
 * User: mew <p />
 * Time: 17/9/11 17:25  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class DocumentTask extends RecursiveTask<Integer> {

    private String document[][];

    private int start, end;

    private String word;

    public DocumentTask(String[][] document, int start, int end, String word) {
        this.document = document;
        this.start = start;
        this.end = end;
        this.word = word;
    }

    @Override
    protected Integer compute() {
        int result = 0;
        if (end - start < 10) {
            result = processLines(document, start, end, word);
        } else {
            int mid = (start + end) / 2;
            DocumentTask task1 = new DocumentTask(document, start, mid, word);
            DocumentTask task2 = new DocumentTask(document, mid, end, word);
            invokeAll(task1, task2);
            try {
                result = groupResults(task1.get(), task2.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private Integer processLines(String[][] document, int start, int end, String word) {
        List<LineTask> tasks = new ArrayList<>();
        for (int i = start; i < end; i++) {
            LineTask lineTask = new LineTask(document[i], 0, document[i].length, word);
            tasks.add(lineTask);
        }
        invokeAll(tasks);
        int result = 0;
        try {
            for (int i = 0; i < tasks.size(); i++) {
                LineTask lineTask = tasks.get(i);
                result = result + lineTask.get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new Integer(result);
    }

    private Integer groupResults(Integer number1, Integer number2) {
        Integer result = number1 + number2;
        return result;
    }

}
