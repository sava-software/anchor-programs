module software.sava.anchor_programs {
  requires java.net.http;

  requires systems.comodal.json_iterator;

  requires software.sava.core;
  requires software.sava.rpc;

  requires software.sava.solana_programs;
  requires software.sava.anchor_src_gen;

  exports software.sava.anchor.programs.drift.anchor;
  exports software.sava.anchor.programs.drift.anchor.types;

  exports software.sava.anchor.programs.marinade;
  exports software.sava.anchor.programs.marinade.anchor;
  exports software.sava.anchor.programs.marinade.anchor.types;

  exports software.sava.anchor.programs.glam;
  exports software.sava.anchor.programs.glam.anchor;
  exports software.sava.anchor.programs.glam.anchor.types;
}
