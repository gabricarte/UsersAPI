package personal.project.users.service.report;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

@Builder
@Slf4j
public class ReportFactoryCSV {
    private List<String> headers;
    private List<List<String>> records;

    public InputStream generate() throws IOException {
        File fileTemp = null;
        OutputStream outputStream = null;
        Writer writer = null;
        BufferedWriter bufferedWriter = null;

        try {
            fileTemp = File.createTempFile("file", ".tmp");
            outputStream = new FileOutputStream(fileTemp);
            writer = new OutputStreamWriter(outputStream, "windows-1252");
            bufferedWriter = new BufferedWriter(writer);

            for (int column = 0; column < headers.size(); column++) {
                bufferedWriter.write(headers.get(column));
                bufferedWriter.write(";");
            }

            bufferedWriter.newLine();

            for (int row = 0; row < records.size(); row++) {
                for (int column = 0; column < records.get(row).size(); column++) {
                    if (records.get(row).get(column) != null) {
                        bufferedWriter.write(records.get(row).get(column).replace(";", ""));
                        bufferedWriter.write(";");
                    } else {
                        bufferedWriter.write(";");
                    }
                }
                bufferedWriter.newLine();
            }
        } catch (Exception e){
            log.error("class=ReportFactoryCSV method=generate Error={}", (Object[])e.getStackTrace());
        } finally {
            if(bufferedWriter != null) bufferedWriter.close();
            if(writer != null) writer.close();
            if(outputStream != null) outputStream.close();
        }

        byte[] file = Files.readAllBytes(fileTemp.toPath());
        if(!fileTemp.delete()) {
            log.error("class=ReportFactoryCSV method=generate Error=Arquivo temporário não excluído.");
        }

        return new ByteArrayInputStream(file);
    }
}
