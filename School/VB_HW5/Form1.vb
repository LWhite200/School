' Class: CS146, Fall 2023
' Student Name: Lukas White
' Instructor: Dr. Cho
' Program: HW5
' Description: A Grading program that finds statistic, average, and invalid answers from students
' Date: December 7, 2023


Public Class Form1
    Private Sub btnValid_Click(sender As Object, e As EventArgs) Handles btnValid.Click
        Dim Valid As Boolean = True 'bool tells the CalculateGrade sub who to display
        Form2.lstBox.Items.Clear()
        Form2.Text = "Valid Scores" 'Names the form
        CalculateGrade(Valid) 'This sub will do all calculations
    End Sub

    Private Sub btnInvalid_Click(sender As Object, e As EventArgs) Handles btnInvalid.Click
        Dim Valid As Boolean = False 'Tells sub that we want invalid grades
        Form2.Text = "Invalid Scores"
        Form2.lstBox.Items.Clear()
        CalculateGrade(Valid)
    End Sub

    Sub CalculateGrade(valid As Boolean) 'A single sub so nothing is repetative/done multiple times
        Dim AnswerKey As String
        Dim exams() As String = IO.File.ReadAllLines("exam.txt") 'Gets the text file 
        AnswerKey = exams(0) 'sets answer key

        For Counting As Integer = 1 To exams.Length - 1 'Iterates through each student exam
            Dim parts() As String = exams(Counting).Split(","c) 'Seperates the pieces of the current exam
            Dim firstName As String = parts(0)
            Dim lastName As String = parts(1)
            Dim id As String = parts(2)
            Dim answer As String = parts(3)
            Dim score As Integer = 0
            Dim scoreOutput As String = "" 'Because the question wants to know whats wrong with certain exams
            Dim canCompute As Boolean = True 'So we can output the desired exams

            If answer.Length = AnswerKey.Length Then 'Makes sure its the desired size
                For i As Integer = 0 To AnswerKey.Length - 1 'Basically the same loop from the last lab
                    If answer(i) = AnswerKey(i) Then 'Adds 1 to the score if question asnwered correctly
                        score += 1
                    End If

                    If answer(i) <> "1" And answer(i) <> "2" And answer(i) <> "3" And answer(i) <> "4" Then 'Some questions didn/'t answer correctly
                        scoreOutput = "Invalid Answer"
                        canCompute = False 'This bool is useful for outputting the correct exams
                        Exit For 'Since we already know the exam is a bad egg
                    End If
                Next
            ElseIf answer.Length >= (AnswerKey.Length) Then
                scoreOutput = "Too Big"
                canCompute = False
            ElseIf answer.Length <= AnswerKey.Length Then
                scoreOutput = "Too Small"
                canCompute = False
            End If

            If canCompute = valid Then 'Valid is the bool set by the buttons
                If canCompute = True Then
                    scoreOutput = score 'score is a string unlike scoreOutput
                End If
                DisplayOutput(firstName, lastName, id, answer, scoreOutput)
            End If
        Next
        Form2.ShowDialog()
    End Sub

    Sub DisplayOutput(a As String, b As String, c As String, d As String, e As String)

        Dim stdName = a & " " & b 'Combined the name as formatting is better
        Dim fmtStr As String = "{0, -14}{1, -11}{2, -22}{3, -25}"
        Form2.lstBox.Items.Add(String.Format(fmtStr, stdName, c, d, e))
    End Sub


    Private Sub btnExam_Click(sender As Object, e As EventArgs) Handles btnExam.Click
        Form2.lstBox.Items.Clear()
        Form2.Text = "Exam File"
        Dim exams() As String = IO.File.ReadAllLines("exam.txt") 'Gets the text file
        Dim fmtStr As String = "{0, -14}{1, -11}{2, -15}{3, -18}"
        For Counting As Integer = 0 To exams.Length - 1 'Iterates through and prints each line of the exam
            Form2.lstBox.Items.Add(exams(Counting))
        Next
        Form2.ShowDialog() 'Displays the form2, the content is already there so its just visually.
    End Sub






    Private Sub btnStats_Click(sender As Object, e As EventArgs) Handles btnStats.Click
        Form2.lstBox.Items.Clear()
        Form2.Text = "Statistics of Class"
        Dim exams() As String = IO.File.ReadAllLines("exam.txt") 'Gets the text file
        Dim AnswerKey As String

        'I could have buntched the following together to look more neat, but kept separate for debugging
        Dim highestStudent As String = "wrongName"
        Dim highestGrade As Integer = 0
        Dim lowestStudent As String = "wrongName"
        Dim lowestGrade As Integer = 20
        Dim average As Double = 0, validAmount As Double = 0
        'validAmount will count the amount of valid answers
        'average will accumilate all valid scores and then be divided by valid amount for real average




        AnswerKey = exams(0) 'sets answer key

        For Counting As Integer = 1 To exams.Length - 1 'Iterates through each student exam
            Dim parts() As String = exams(Counting).Split(","c) 'Seperates the pieces of the current exam
            Dim firstName As String = parts(0)
            Dim lastName As String = parts(1)
            Dim answer As String = parts(3)
            Dim score As Integer = 0
            Dim canCompute As Boolean = True
            If answer.Length = AnswerKey.Length Then 'Makes sure its the desired size
                For i As Integer = 0 To AnswerKey.Length - 1 'Basically the same loop from the last lab
                    If answer(i) = AnswerKey(i) Then 'Adds 1 to the score if question asnwered correctly
                        score += 1
                    End If
                    If answer(i) <> "1" And answer(i) <> "2" And answer(i) <> "3" And answer(i) <> "4" Then
                        canCompute = False
                        Exit For
                    End If
                Next
            Else
                canCompute = False
            End If

            'This will only take place if there is a valid answer 'sheet'
            If canCompute Then
                average += score
                validAmount += 1
                'As mentioned earlier, this is to find class average.

                'This replaces old highest/lowest scores with the correct one with linear search.
                If score > highestGrade Then
                    highestGrade = score
                    highestStudent = firstName & " " & lastName
                End If
                If score < lowestGrade Then
                    lowestGrade = score
                    lowestStudent = firstName & " " & lastName
                End If
            End If
        Next

        Form2.lstBox.Items.Add("Top Score: " & highestStudent & ", " & highestGrade)
        Form2.lstBox.Items.Add("Lowest Score: " & lowestStudent & ", " & lowestGrade)
        Form2.lstBox.Items.Add("Average Score: " & (average / validAmount)) 'Where average is calculated
        Form2.ShowDialog()
    End Sub

    Private Sub btnPoints_Click(sender As Object, e As EventArgs) Handles btnPoints.Click
        Form2.lstBox.Items.Clear()
        Form2.Text = "Grade Points"
        Dim exams() As String = IO.File.ReadAllLines("exam.txt") 'Gets the text file
        Dim AnswerKey As String
        AnswerKey = exams(0) 'sets answer key

        For Counting As Integer = 1 To exams.Length - 1 'Iterates through each student exam
            Dim parts() As String = exams(Counting).Split(","c) 'Seperates the pieces of the current exam
            Dim firstName As String = parts(0)
            Dim lastName As String = parts(1)
            Dim answer As String = parts(3)
            Dim score As Integer = 0
            Dim canCompute As Boolean = True
            If answer.Length = AnswerKey.Length Then 'Makes sure its the desired size
                For i As Integer = 0 To AnswerKey.Length - 1 'Basically the same loop from the last lab
                    If answer(i) = AnswerKey(i) Then 'Adds 1 to the score if question asnwered correctly
                        score += 1
                    End If
                    If answer(i) <> "1" And answer(i) <> "2" And answer(i) <> "3" And answer(i) <> "4" Then
                        canCompute = False
                        Exit For
                    End If
                Next
            Else
                canCompute = False
            End If

            'The following will asign each student a letter grade and print it, which depends on their score
            If canCompute Then
                Dim letterGrade As String
                If score >= 19 Then
                    letterGrade = "A"
                ElseIf score >= 17 Then
                    letterGrade = "B"
                ElseIf score >= 16 Then
                    letterGrade = "C"
                ElseIf score >= 15 Then
                    letterGrade = "D"
                Else
                    letterGrade = "F"
                End If

                Dim fmtStr As String = "{0, -14}{1, -11}"
                Form2.lstBox.Items.Add(String.Format(fmtStr, firstName & " " & lastName, letterGrade))

            End If
        Next
        Form2.ShowDialog()
    End Sub
End Class