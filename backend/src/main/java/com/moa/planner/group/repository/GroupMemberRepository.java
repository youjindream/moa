package com.moa.planner.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moa.planner.group.entitiy.GroupMember;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long>{

}
