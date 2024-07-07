package com.example.JDBCplusMVCminiProjectForMyself.Controller;

import com.example.JDBCplusMVCminiProjectForMyself.relationaldataaccess.toDoList;
import com.example.JDBCplusMVCminiProjectForMyself.relationaldataaccess.toDoListDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/list")
public class ListController {
    private final toDoListDAO toDoListDAO;

    @Autowired
    public ListController(toDoListDAO toDoListDAO) {
        this.toDoListDAO = toDoListDAO;
    }

    @GetMapping
    public String showAllList(Model model){
        model.addAttribute("toDoList",toDoListDAO.getAllToDoList());
        return "list";
    }


    @PostMapping("/addTask")
    public String addToDoList(@RequestParam String title
    , @RequestParam String description
    , @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        toDoList toDoList = new toDoList();
        toDoList.setTitle(title);
        toDoList.setDescription(description);
        toDoList.setDate(String.valueOf(date));
        toDoListDAO.addTask(toDoList);
        return "redirect:/list";
    }

    @PostMapping("/deleteTask")
    public String deleteTask(@RequestParam int id){
        toDoListDAO.deletTaskByID(id);
        return "redirect:/list";
    }

    @PostMapping("/editTask")
    public String editTask(@RequestParam int id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String str){
        Boolean isDone = Boolean.valueOf(str);
        toDoListDAO.updateByID(id,title,description,date,isDone);
        return "redirect:/list";
    }

    @PostMapping("/markDone/{id}")
    public String checkTask(@PathVariable int id, @RequestParam(required = false) Boolean isDone){
        toDoList task = toDoListDAO.getToDoList(id);
        if (task != null) {
            task.setDone(!task.isDone()); // Инвертировать статус задачи
            toDoListDAO.updateTask(task);
        }
        return "redirect:/list";
    }
}
