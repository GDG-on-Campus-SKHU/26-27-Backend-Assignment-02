package root.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.dto.SchoolClassInfoResponseDto;
import root.dto.SchoolClassSaveRequestDto;
import root.service.SchoolClassService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schoolClasses")
public class SchoolClassController {

    private final SchoolClassService schoolClassService;

    @PostMapping
    public ResponseEntity<SchoolClassInfoResponseDto> saveSchoolClass(@RequestBody SchoolClassSaveRequestDto schoolClassSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(schoolClassService.saveSchoolClass(schoolClassSaveRequestDto));
    }

    @GetMapping("/{schoolClassId}")
    public ResponseEntity<SchoolClassInfoResponseDto> getSchoolClass(@PathVariable Long schoolClassId) {
        return ResponseEntity.status(HttpStatus.OK).body(schoolClassService.getSchoolClass(schoolClassId));
    }

    @PatchMapping("/{schoolClassId}")
    public ResponseEntity<SchoolClassInfoResponseDto> updateSchoolClass(@PathVariable Long schoolClassId,
                                                                        @RequestBody SchoolClassSaveRequestDto schoolClassSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(schoolClassService.updateSchoolClass(schoolClassId, schoolClassSaveRequestDto));
    }

    @DeleteMapping("/{schoolClassId}")
    public ResponseEntity<Void> deleteSchoolClass(@PathVariable Long schoolClassId) {
        schoolClassService.deleteSchoolClass(schoolClassId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<SchoolClassInfoResponseDto>> getAllSchoolClass() {
        return ResponseEntity.status(HttpStatus.OK).body(schoolClassService.getAllSchoolClass());
    }
}
