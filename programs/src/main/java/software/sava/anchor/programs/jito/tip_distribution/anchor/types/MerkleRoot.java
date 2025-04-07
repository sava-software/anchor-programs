package software.sava.anchor.programs.jito.tip_distribution.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record MerkleRoot(// The 256-bit merkle root.
                         byte[] root,
                         // Maximum number of funds that can ever be claimed from this [MerkleRoot].
                         long maxTotalClaim,
                         // Maximum number of nodes that can ever be claimed from this [MerkleRoot].
                         long maxNumNodes,
                         // Total funds that have been claimed.
                         long totalFundsClaimed,
                         // Number of nodes that have been claimed.
                         long numNodesClaimed) implements Borsh {

  public static final int BYTES = 64;

  public static MerkleRoot read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var root = new byte[32];
    i += Borsh.readArray(root, _data, i);
    final var maxTotalClaim = getInt64LE(_data, i);
    i += 8;
    final var maxNumNodes = getInt64LE(_data, i);
    i += 8;
    final var totalFundsClaimed = getInt64LE(_data, i);
    i += 8;
    final var numNodesClaimed = getInt64LE(_data, i);
    return new MerkleRoot(root,
                          maxTotalClaim,
                          maxNumNodes,
                          totalFundsClaimed,
                          numNodesClaimed);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(root, _data, i);
    putInt64LE(_data, i, maxTotalClaim);
    i += 8;
    putInt64LE(_data, i, maxNumNodes);
    i += 8;
    putInt64LE(_data, i, totalFundsClaimed);
    i += 8;
    putInt64LE(_data, i, numNodesClaimed);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
