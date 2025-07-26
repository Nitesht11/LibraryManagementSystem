package com.librarymanagement.Respository;

import com.librarymanagement.Entity.IssueRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRecordRepository  extends JpaRepository <IssueRecord,Long >  {
}
