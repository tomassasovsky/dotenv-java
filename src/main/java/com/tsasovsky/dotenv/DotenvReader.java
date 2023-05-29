package com.tsasovsky.dotenv;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * (Internal) Reads a .env file
 */
public class DotenvReader {
    private final String directory;
    private final String filename;

    /**
     * Creates a dotenv reader
     *
     * @param directory the directory containing the .env file
     * @param filename  the file name of the .env file e.g. .env
     */
    public DotenvReader(String directory, String filename) {
        this.directory = directory;
        this.filename = filename;
    }

    /**
     * (Internal) Reads the .env file
     *
     * @return a list containing the contents of each line in the .env file
     * @throws DotenvException if a dotenv error occurs
     */
    public List<String> readFromfile(AssetManager assetManager) throws DotenvException {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        String fileName;
        if (directory != null && !directory.isEmpty()) {
            fileName = directory + "/" + filename;
        } else {
            fileName = filename;
        }

        try {
            fIn = assetManager.open(fileName);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line;
            while ((line = input.readLine()) != null) {
                if (returnString.length() > 0) {
                    returnString.append("\n").append(line);
                } else {
                    returnString.append(line);
                }
            }
        } catch (IOException e) {
            Log.e("readFromFile", e.getMessage());
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (IOException e2) {
                Log.e("readFromFile", e2.getMessage());
            }
        }
        return List.of(returnString.toString().split("\n"));
    }
}
