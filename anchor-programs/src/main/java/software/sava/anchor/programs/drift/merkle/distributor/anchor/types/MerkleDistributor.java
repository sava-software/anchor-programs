package software.sava.anchor.programs.drift.merkle.distributor.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

// State for the account which distributes tokens.
public record MerkleDistributor(PublicKey _address,
                                Discriminator discriminator,
                                // Bump seed.
                                int bump,
                                // Version of the airdrop
                                long version,
                                // The 256-bit merkle root.
                                byte[] root,
                                // [Mint] of the token to be distributed.
                                PublicKey mint,
                                // Token Address of the vault
                                PublicKey tokenVault,
                                // Maximum number of tokens that can ever be claimed from this [MerkleDistributor].
                                long maxTotalClaim,
                                // Maximum number of nodes in [MerkleDistributor].
                                long maxNumNodes,
                                // Total amount of tokens that have been claimed.
                                long totalAmountClaimed,
                                // Total amount of tokens that have been forgone.
                                long totalAmountForgone,
                                // Number of nodes that have been claimed.
                                long numNodesClaimed,
                                // Lockup time start (Unix Timestamp)
                                long startTs,
                                // Lockup time end (Unix Timestamp)
                                long endTs,
                                // Clawback start (Unix Timestamp)
                                long clawbackStartTs,
                                // Clawback receiver
                                PublicKey clawbackReceiver,
                                // Admin wallet
                                PublicKey admin,
                                // Whether or not the distributor has been clawed back
                                boolean clawedBack,
                                // this merkle tree is enable from this slot
                                long enableSlot,
                                // indicate that whether admin can close this pool, for testing purpose
                                boolean closable,
                                // Buffer 0
                                byte[] buffer0,
                                // Buffer 1
                                byte[] buffer1,
                                // Buffer 2
                                byte[] buffer2) implements Borsh {

  public static final int BYTES = 347;
  public static final int ROOT_LEN = 32;
  public static final int BUFFER_0_LEN = 32;
  public static final int BUFFER_1_LEN = 32;
  public static final int BUFFER_2_LEN = 32;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int BUMP_OFFSET = 8;
  public static final int VERSION_OFFSET = 9;
  public static final int ROOT_OFFSET = 17;
  public static final int MINT_OFFSET = 49;
  public static final int TOKEN_VAULT_OFFSET = 81;
  public static final int MAX_TOTAL_CLAIM_OFFSET = 113;
  public static final int MAX_NUM_NODES_OFFSET = 121;
  public static final int TOTAL_AMOUNT_CLAIMED_OFFSET = 129;
  public static final int TOTAL_AMOUNT_FORGONE_OFFSET = 137;
  public static final int NUM_NODES_CLAIMED_OFFSET = 145;
  public static final int START_TS_OFFSET = 153;
  public static final int END_TS_OFFSET = 161;
  public static final int CLAWBACK_START_TS_OFFSET = 169;
  public static final int CLAWBACK_RECEIVER_OFFSET = 177;
  public static final int ADMIN_OFFSET = 209;
  public static final int CLAWED_BACK_OFFSET = 241;
  public static final int ENABLE_SLOT_OFFSET = 242;
  public static final int CLOSABLE_OFFSET = 250;
  public static final int BUFFER_0_OFFSET = 251;
  public static final int BUFFER_1_OFFSET = 283;
  public static final int BUFFER_2_OFFSET = 315;

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createVersionFilter(final long version) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, version);
    return Filter.createMemCompFilter(VERSION_OFFSET, _data);
  }

  public static Filter createMintFilter(final PublicKey mint) {
    return Filter.createMemCompFilter(MINT_OFFSET, mint);
  }

  public static Filter createTokenVaultFilter(final PublicKey tokenVault) {
    return Filter.createMemCompFilter(TOKEN_VAULT_OFFSET, tokenVault);
  }

  public static Filter createMaxTotalClaimFilter(final long maxTotalClaim) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxTotalClaim);
    return Filter.createMemCompFilter(MAX_TOTAL_CLAIM_OFFSET, _data);
  }

  public static Filter createMaxNumNodesFilter(final long maxNumNodes) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxNumNodes);
    return Filter.createMemCompFilter(MAX_NUM_NODES_OFFSET, _data);
  }

  public static Filter createTotalAmountClaimedFilter(final long totalAmountClaimed) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalAmountClaimed);
    return Filter.createMemCompFilter(TOTAL_AMOUNT_CLAIMED_OFFSET, _data);
  }

  public static Filter createTotalAmountForgoneFilter(final long totalAmountForgone) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalAmountForgone);
    return Filter.createMemCompFilter(TOTAL_AMOUNT_FORGONE_OFFSET, _data);
  }

  public static Filter createNumNodesClaimedFilter(final long numNodesClaimed) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, numNodesClaimed);
    return Filter.createMemCompFilter(NUM_NODES_CLAIMED_OFFSET, _data);
  }

  public static Filter createStartTsFilter(final long startTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, startTs);
    return Filter.createMemCompFilter(START_TS_OFFSET, _data);
  }

  public static Filter createEndTsFilter(final long endTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, endTs);
    return Filter.createMemCompFilter(END_TS_OFFSET, _data);
  }

  public static Filter createClawbackStartTsFilter(final long clawbackStartTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, clawbackStartTs);
    return Filter.createMemCompFilter(CLAWBACK_START_TS_OFFSET, _data);
  }

  public static Filter createClawbackReceiverFilter(final PublicKey clawbackReceiver) {
    return Filter.createMemCompFilter(CLAWBACK_RECEIVER_OFFSET, clawbackReceiver);
  }

  public static Filter createAdminFilter(final PublicKey admin) {
    return Filter.createMemCompFilter(ADMIN_OFFSET, admin);
  }

  public static Filter createClawedBackFilter(final boolean clawedBack) {
    return Filter.createMemCompFilter(CLAWED_BACK_OFFSET, new byte[]{(byte) (clawedBack ? 1 : 0)});
  }

  public static Filter createEnableSlotFilter(final long enableSlot) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, enableSlot);
    return Filter.createMemCompFilter(ENABLE_SLOT_OFFSET, _data);
  }

  public static Filter createClosableFilter(final boolean closable) {
    return Filter.createMemCompFilter(CLOSABLE_OFFSET, new byte[]{(byte) (closable ? 1 : 0)});
  }

  public static MerkleDistributor read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static MerkleDistributor read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static MerkleDistributor read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], MerkleDistributor> FACTORY = MerkleDistributor::read;

  public static MerkleDistributor read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var bump = _data[i] & 0xFF;
    ++i;
    final var version = getInt64LE(_data, i);
    i += 8;
    final var root = new byte[32];
    i += Borsh.readArray(root, _data, i);
    final var mint = readPubKey(_data, i);
    i += 32;
    final var tokenVault = readPubKey(_data, i);
    i += 32;
    final var maxTotalClaim = getInt64LE(_data, i);
    i += 8;
    final var maxNumNodes = getInt64LE(_data, i);
    i += 8;
    final var totalAmountClaimed = getInt64LE(_data, i);
    i += 8;
    final var totalAmountForgone = getInt64LE(_data, i);
    i += 8;
    final var numNodesClaimed = getInt64LE(_data, i);
    i += 8;
    final var startTs = getInt64LE(_data, i);
    i += 8;
    final var endTs = getInt64LE(_data, i);
    i += 8;
    final var clawbackStartTs = getInt64LE(_data, i);
    i += 8;
    final var clawbackReceiver = readPubKey(_data, i);
    i += 32;
    final var admin = readPubKey(_data, i);
    i += 32;
    final var clawedBack = _data[i] == 1;
    ++i;
    final var enableSlot = getInt64LE(_data, i);
    i += 8;
    final var closable = _data[i] == 1;
    ++i;
    final var buffer0 = new byte[32];
    i += Borsh.readArray(buffer0, _data, i);
    final var buffer1 = new byte[32];
    i += Borsh.readArray(buffer1, _data, i);
    final var buffer2 = new byte[32];
    Borsh.readArray(buffer2, _data, i);
    return new MerkleDistributor(_address,
                                 discriminator,
                                 bump,
                                 version,
                                 root,
                                 mint,
                                 tokenVault,
                                 maxTotalClaim,
                                 maxNumNodes,
                                 totalAmountClaimed,
                                 totalAmountForgone,
                                 numNodesClaimed,
                                 startTs,
                                 endTs,
                                 clawbackStartTs,
                                 clawbackReceiver,
                                 admin,
                                 clawedBack,
                                 enableSlot,
                                 closable,
                                 buffer0,
                                 buffer1,
                                 buffer2);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    _data[i] = (byte) bump;
    ++i;
    putInt64LE(_data, i, version);
    i += 8;
    i += Borsh.writeArrayChecked(root, 32, _data, i);
    mint.write(_data, i);
    i += 32;
    tokenVault.write(_data, i);
    i += 32;
    putInt64LE(_data, i, maxTotalClaim);
    i += 8;
    putInt64LE(_data, i, maxNumNodes);
    i += 8;
    putInt64LE(_data, i, totalAmountClaimed);
    i += 8;
    putInt64LE(_data, i, totalAmountForgone);
    i += 8;
    putInt64LE(_data, i, numNodesClaimed);
    i += 8;
    putInt64LE(_data, i, startTs);
    i += 8;
    putInt64LE(_data, i, endTs);
    i += 8;
    putInt64LE(_data, i, clawbackStartTs);
    i += 8;
    clawbackReceiver.write(_data, i);
    i += 32;
    admin.write(_data, i);
    i += 32;
    _data[i] = (byte) (clawedBack ? 1 : 0);
    ++i;
    putInt64LE(_data, i, enableSlot);
    i += 8;
    _data[i] = (byte) (closable ? 1 : 0);
    ++i;
    i += Borsh.writeArrayChecked(buffer0, 32, _data, i);
    i += Borsh.writeArrayChecked(buffer1, 32, _data, i);
    i += Borsh.writeArrayChecked(buffer2, 32, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
