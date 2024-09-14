module software.sava.anchor_programs {
  exports software.sava.anchor.programs.drift.anchor.types;
  exports software.sava.anchor.programs.drift.anchor;
  exports software.sava.anchor.programs.drift;
  exports software.sava.anchor.programs.glam.anchor.types;
  exports software.sava.anchor.programs.glam.anchor;
  exports software.sava.anchor.programs.glam;
  exports software.sava.anchor.programs.jupiter.dca.anchor.types;
  exports software.sava.anchor.programs.jupiter.dca.anchor;
  exports software.sava.anchor.programs.jupiter.limit.anchor.types;
  exports software.sava.anchor.programs.jupiter.limit.anchor;
  exports software.sava.anchor.programs.jupiter.swap.anchor.types;
  exports software.sava.anchor.programs.jupiter.swap.anchor;
  exports software.sava.anchor.programs.jupiter;
  exports software.sava.anchor.programs.marinade.anchor.types;
  exports software.sava.anchor.programs.marinade.anchor;
  exports software.sava.anchor.programs.marinade;
  requires java.net.http;
  requires org.bouncycastle.provider;
  requires software.sava.anchor_src_gen;
  requires software.sava.core;
  requires software.sava.rpc;
  requires software.sava.solana_programs;
  requires systems.comodal.json_iterator;
}
