package pro1.apiDataModel;

import com.google.gson.annotations.SerializedName;

public class Exam {
    @SerializedName("obsazeni")
    public String studentsCount;

    @SerializedName("ucitIdno")
    public Long teacherId;

    public long getStudentsCountValue() {
        if (studentsCount == null || studentsCount.isBlank()) {
            return 0;
        }
        return Long.parseLong(studentsCount);
    }
}
