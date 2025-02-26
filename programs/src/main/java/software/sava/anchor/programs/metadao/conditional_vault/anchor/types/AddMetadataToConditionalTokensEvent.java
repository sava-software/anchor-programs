package software.sava.anchor.programs.metadao.conditional_vault.anchor.types;

import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record AddMetadataToConditionalTokensEvent(CommonFields common,
                                                  PublicKey vault,
                                                  PublicKey conditionalTokenMint,
                                                  PublicKey conditionalTokenMetadata,
                                                  String name, byte[] _name,
                                                  String symbol, byte[] _symbol,
                                                  String uri, byte[] _uri,
                                                  long seqNum) implements Borsh {

  public static AddMetadataToConditionalTokensEvent createRecord(final CommonFields common,
                                                                 final PublicKey vault,
                                                                 final PublicKey conditionalTokenMint,
                                                                 final PublicKey conditionalTokenMetadata,
                                                                 final String name,
                                                                 final String symbol,
                                                                 final String uri,
                                                                 final long seqNum) {
    return new AddMetadataToConditionalTokensEvent(common,
                                                   vault,
                                                   conditionalTokenMint,
                                                   conditionalTokenMetadata,
                                                   name, name.getBytes(UTF_8),
                                                   symbol, symbol.getBytes(UTF_8),
                                                   uri, uri.getBytes(UTF_8),
                                                   seqNum);
  }

  public static AddMetadataToConditionalTokensEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var common = CommonFields.read(_data, i);
    i += Borsh.len(common);
    final var vault = readPubKey(_data, i);
    i += 32;
    final var conditionalTokenMint = readPubKey(_data, i);
    i += 32;
    final var conditionalTokenMetadata = readPubKey(_data, i);
    i += 32;
    final var name = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var symbol = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var uri = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var seqNum = getInt64LE(_data, i);
    return new AddMetadataToConditionalTokensEvent(common,
                                                   vault,
                                                   conditionalTokenMint,
                                                   conditionalTokenMetadata,
                                                   name, name.getBytes(UTF_8),
                                                   symbol, symbol.getBytes(UTF_8),
                                                   uri, uri.getBytes(UTF_8),
                                                   seqNum);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(common, _data, i);
    vault.write(_data, i);
    i += 32;
    conditionalTokenMint.write(_data, i);
    i += 32;
    conditionalTokenMetadata.write(_data, i);
    i += 32;
    i += Borsh.writeVector(_name, _data, i);
    i += Borsh.writeVector(_symbol, _data, i);
    i += Borsh.writeVector(_uri, _data, i);
    putInt64LE(_data, i, seqNum);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(common)
         + 32
         + 32
         + 32
         + Borsh.lenVector(_name)
         + Borsh.lenVector(_symbol)
         + Borsh.lenVector(_uri)
         + 8;
  }
}
