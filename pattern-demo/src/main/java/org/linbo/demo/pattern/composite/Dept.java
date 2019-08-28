package org.linbo.demo.pattern.composite;

import java.util.Arrays;
import java.util.List;

/**
 * 部门数据结构是一个典型的组合模式
 * @author 71085
 *
 */
public class Dept {

	/**
	 * 部门ID
	 */
	private String id;
	
	/**
	 * 部门名称
	 */
	private String name;
	
	/**
	 * 下一级部门
	 */
	private List<Dept> children;
	
	public Dept(String id, String name, List<Dept> children) {
		super();
		this.id = id;
		this.name = name;
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Dept> getChildren() {
		return children;
	}

	public void setChildren(List<Dept> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "Dept [id=" + id + ", name=" + name + ", children=" + children + "]";
	}
	
	public static void main(String[] args) {
		Dept c = new Dept("2", "xxx部门", null);
		Dept p = new Dept("1", "xxx公司", Arrays.asList(c));
		System.out.println(p);
	}
}
