package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

// Side of resize, 0 for lower and 1 for upper
public enum ResizeSide implements Borsh.Enum {

  Lower,
  Upper;

  public static ResizeSide read(final byte[] _data, final int offset) {
    return Borsh.read(ResizeSide.values(), _data, offset);
  }
}