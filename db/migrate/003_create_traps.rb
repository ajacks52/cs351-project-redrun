require 'active_record'

# Migration to create the Traps tables
class CreateTraps < ActiveRecord::Migration

  def change
    create_table :traps do |t|
      t.column :type, :string, null: false
      t.belongs_to :kiosk, null: false
    end
    add_index :traps, [:type], :name => "index_traps_on_type"
    add_index :traps, [:kiosk_id], :name => "index_traps_on_kiosk_id"
  end
end