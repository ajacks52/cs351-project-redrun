require 'active_record'

# Migration to create the Characters tables
class CreateMapobjects < ActiveRecord::Migration

  def change
    create_table :mapobjects do |t|
      t.column :object_name, :string, null: false
      t.column :location, :string, null: false
      t.column :texture, :string, null: false
      t.column :direction, :string, null: false
      t.belongs_to :map, null: false
    end
    add_index :mapobjects, [:object_name], :name => "index_mapobjects_on_object_name"
    add_index :mapobjects, [:location], :name => "index_mapobjects_on_location"
    add_index :mapobjects, [:texture], :name => "index_mapobjects_on_texture"
    add_index :mapobjects, [:direction], :name => "index_mapobjects_on_direction"
    add_index :mapobjects, [:map_id], :name => "index_mapobjects_on_map_id"
  end
end