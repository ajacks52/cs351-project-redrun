require 'active_record'

# Migration to create the Traps tables
class CreateTraps < ActiveRecord::Migration

  def change
    create_table :traps do |t|
      t.column :trap_type, :string, null: false
      t.belongs_to :kiosk, null: false
      t.belongs_to :map, null: false
    end
    add_index :traps, [:trap_type], :name => "index_traps_on_trap_type"
    add_index :traps, [:kiosk_id], :name => "index_traps_on_kiosk_id"
    add_index :traps, [:map_id], :name => "index_traps_on_map_id"
  end
end